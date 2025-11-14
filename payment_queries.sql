-- =====================================================
-- SQL QUERIES FOR USER PAYMENT WITH DISCOUNT (GIẢM GIÁ)
-- =====================================================

-- 1. CREATE TABLE: Discounts/Promotions (Giảm giá)
CREATE TABLE IF NOT EXISTS discounts (
    discount_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    discount_percent DECIMAL(5,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. CREATE TABLE: Booking Discount Link (Liên kết giảm giá và booking)
CREATE TABLE IF NOT EXISTS booking_discount (
    booking_id BIGINT NOT NULL,
    discount_id BIGINT NOT NULL,
    PRIMARY KEY (booking_id, discount_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (discount_id) REFERENCES discounts(discount_id) ON DELETE CASCADE
);

-- =====================================================
-- QUERY 1: Get discount information by code
-- Lấy thông tin giảm giá theo mã code
-- =====================================================
SELECT 
    discount_id,
    code,
    name,
    discount_percent,
    start_date,
    end_date,
    is_active
FROM discounts
WHERE code = 'CODE123' 
AND is_active = TRUE
AND CURDATE() BETWEEN start_date AND end_date;

-- =====================================================
-- QUERY 2: Get user payment with discount
-- Lấy thông tin thanh toán của user với giảm giá
-- =====================================================
SELECT 
    u.user_id,
    u.full_name,
    u.email,
    u.phone,
    b.booking_id,
    b.booking_code,
    h.name AS hotel_name,
    b.checkin_date,
    b.checkout_date,
    b.nights,
    b.final_price AS original_total_price,
    COALESCE(d.discount_percent, 0) AS discount_percent,
    COALESCE(d.code, 'NONE') AS discount_code,
    COALESCE((b.final_price * (d.discount_percent / 100)), 0) AS discount_amount,
    (b.final_price - COALESCE((b.final_price * (d.discount_percent / 100)), 0)) AS final_payment_amount,
    p.payment_id,
    p.amount AS payment_amount,
    p.payment_gateway,
    p.status AS payment_status,
    p.created_at AS payment_date
FROM users u
INNER JOIN bookings b ON u.user_id = b.user_id
INNER JOIN hotel h ON b.hotel_id = h.hotel_id
LEFT JOIN booking_discount bd ON b.booking_id = bd.booking_id
LEFT JOIN discounts d ON bd.discount_id = d.discount_id
LEFT JOIN payments p ON b.booking_id = p.booking_id
WHERE u.user_id = 1
ORDER BY b.created_at DESC;

-- =====================================================
-- QUERY 3: Calculate payment amount with discount
-- Tính số tiền thanh toán với giảm giá
-- =====================================================
SELECT 
    b.booking_id,
    b.final_price AS original_price,
    COALESCE(d.discount_percent, 0) AS discount_percent,
    COALESCE(d.code, 'NONE') AS discount_code,
    (b.final_price * (COALESCE(d.discount_percent, 0) / 100)) AS discount_amount,
    (b.final_price - (b.final_price * (COALESCE(d.discount_percent, 0) / 100))) AS final_payment_amount
FROM bookings b
LEFT JOIN booking_discount bd ON b.booking_id = bd.booking_id
LEFT JOIN discounts d ON bd.discount_id = d.discount_id
WHERE b.booking_id = 1;

-- =====================================================
-- QUERY 4: Apply discount code to booking
-- Áp dụng mã giảm giá cho đơn booking
-- =====================================================
INSERT INTO booking_discount (booking_id, discount_id)
SELECT 1, discount_id
FROM discounts
WHERE code = 'CODE123'
AND is_active = TRUE
AND CURDATE() BETWEEN start_date AND end_date
ON DUPLICATE KEY UPDATE discount_id = VALUES(discount_id);

-- =====================================================
-- QUERY 5: Create payment with discount
-- Tạo thanh toán với giảm giá
-- =====================================================
INSERT INTO payments (
    booking_id,
    amount,
    payment_gateway,
    status,
    created_at
)
SELECT 
    b.booking_id,
    (b.final_price - (b.final_price * (COALESCE(d.discount_percent, 0) / 100))) AS amount,
    'credit_card',
    'pending',
    CURRENT_TIMESTAMP
FROM bookings b
LEFT JOIN booking_discount bd ON b.booking_id = bd.booking_id
LEFT JOIN discounts d ON bd.discount_id = d.discount_id
WHERE b.booking_id = 1;

-- =====================================================
-- QUERY 6: Get all active discount codes
-- Lấy tất cả mã giảm giá đang hoạt động
-- =====================================================
SELECT 
    discount_id,
    code,
    name,
    discount_percent,
    start_date,
    end_date,
    DATEDIFF(end_date, CURDATE()) AS days_remaining
FROM discounts
WHERE is_active = TRUE
AND CURDATE() BETWEEN start_date AND end_date
ORDER BY discount_percent DESC;

-- =====================================================
-- QUERY 7: Remove discount from booking
-- Xóa giảm giá khỏi đơn booking
-- =====================================================
DELETE FROM booking_discount
WHERE booking_id = 1;

-- =====================================================
-- QUERY 8: Update payment amount when discount changed
-- Cập nhật số tiền thanh toán khi giảm giá thay đổi
-- =====================================================
UPDATE payments p
SET p.amount = (
    SELECT (b.final_price - (b.final_price * (COALESCE(d.discount_percent, 0) / 100)))
    FROM bookings b
    LEFT JOIN booking_discount bd ON b.booking_id = bd.booking_id
    LEFT JOIN discounts d ON bd.discount_id = d.discount_id
    WHERE b.booking_id = p.booking_id
)
WHERE p.booking_id = 1
AND p.status = 'pending';

-- =====================================================
-- QUERY 9: Get user payment summary with discount
-- Lấy tóm tắt thanh toán của user có giảm giá
-- =====================================================
SELECT 
    u.user_id,
    u.full_name,
    u.email,
    COUNT(DISTINCT b.booking_id) AS total_bookings,
    COUNT(DISTINCT CASE WHEN bd.booking_id IS NOT NULL THEN b.booking_id END) AS bookings_with_discount,
    SUM(b.final_price) AS total_original_amount,
    SUM(COALESCE((b.final_price * (d.discount_percent / 100)), 0)) AS total_discount_amount,
    SUM(COALESCE((b.final_price * (d.discount_percent / 100)), 0)) / SUM(b.final_price) * 100 AS avg_discount_percent,
    SUM(p.amount) AS total_paid_amount
FROM users u
LEFT JOIN bookings b ON u.user_id = b.user_id
LEFT JOIN booking_discount bd ON b.booking_id = bd.booking_id
LEFT JOIN discounts d ON bd.discount_id = d.discount_id
LEFT JOIN payments p ON b.booking_id = p.booking_id
WHERE u.user_id = 1
GROUP BY u.user_id, u.full_name, u.email;

-- =====================================================
-- QUERY 10: Get discount usage statistics
-- Lấy thống kê sử dụng mã giảm giá
-- =====================================================
SELECT 
    d.discount_id,
    d.code,
    d.name,
    d.discount_percent,
    COUNT(DISTINCT bd.booking_id) AS usage_count,
    COUNT(DISTINCT b.user_id) AS unique_users,
    SUM(COALESCE((b.final_price * (d.discount_percent / 100)), 0)) AS total_discount_given,
    AVG(COALESCE((b.final_price * (d.discount_percent / 100)), 0)) AS avg_discount_per_booking,
    SUM(p.amount) AS revenue_after_discount
FROM discounts d
LEFT JOIN booking_discount bd ON d.discount_id = bd.discount_id
LEFT JOIN bookings b ON bd.booking_id = b.booking_id
LEFT JOIN payments p ON b.booking_id = p.booking_id
WHERE d.is_active = TRUE
GROUP BY d.discount_id, d.code, d.name, d.discount_percent
ORDER BY usage_count DESC;

-- =====================================================
-- QUERY 11: Get payment history of user with discount breakdown
-- Lấy lịch sử thanh toán của user với chi tiết giảm giá
-- =====================================================
SELECT 
    p.payment_id,
    p.booking_id,
    b.booking_code,
    u.user_id,
    u.full_name,
    u.email,
    b.checkin_date,
    b.checkout_date,
    b.final_price AS original_amount,
    COALESCE(d.discount_percent, 0) AS discount_percent,
    COALESCE(d.code, 'NONE') AS discount_code,
    (b.final_price * (COALESCE(d.discount_percent, 0) / 100)) AS discount_amount,
    p.amount AS final_payment_amount,
    p.payment_gateway,
    p.status,
    p.created_at AS payment_date
FROM payments p
INNER JOIN bookings b ON p.booking_id = b.booking_id
INNER JOIN users u ON b.user_id = u.user_id
LEFT JOIN booking_discount bd ON b.booking_id = bd.booking_id
LEFT JOIN discounts d ON bd.discount_id = d.discount_id
WHERE u.user_id = 1
ORDER BY p.created_at DESC;

-- =====================================================
-- QUERY 12: Validate discount code before applying
-- Kiểm tra mã giảm giá có hợp lệ trước khi áp dụng
-- =====================================================
SELECT 
    discount_id,
    code,
    name,
    discount_percent,
    start_date,
    end_date,
    CASE 
        WHEN is_active = FALSE THEN 'Discount is inactive'
        WHEN CURDATE() < start_date THEN 'Discount not yet active'
        WHEN CURDATE() > end_date THEN 'Discount expired'
        ELSE 'Valid'
    END AS status
FROM discounts
WHERE code = 'CODE123';

-- =====================================================
-- QUERY 13: Get daily payment revenue with discount impact
-- Lấy doanh thu hàng ngày có ảnh hưởng của giảm giá
-- =====================================================
SELECT 
    DATE(p.created_at) AS payment_date,
    COUNT(DISTINCT b.booking_id) AS total_bookings,
    COUNT(DISTINCT CASE WHEN bd.booking_id IS NOT NULL THEN b.booking_id END) AS bookings_with_discount,
    SUM(b.final_price) AS original_revenue,
    SUM(COALESCE((b.final_price * (d.discount_percent / 100)), 0)) AS discount_amount,
    SUM(p.amount) AS actual_revenue,
    (SUM(COALESCE((b.final_price * (d.discount_percent / 100)), 0)) / SUM(b.final_price) * 100) AS discount_impact_percent
FROM payments p
INNER JOIN bookings b ON p.booking_id = b.booking_id
LEFT JOIN booking_discount bd ON b.booking_id = bd.booking_id
LEFT JOIN discounts d ON bd.discount_id = d.discount_id
WHERE p.status IN ('completed', 'pending')
GROUP BY DATE(p.created_at)
ORDER BY payment_date DESC;

-- =====================================================
-- QUERY 14: Verify user payment with discount details
-- Xác minh thanh toán của user với chi tiết giảm giá
-- =====================================================
SELECT 
    b.booking_id,
    b.booking_code,
    u.full_name,
    u.email,
    h.name AS hotel_name,
    b.final_price,
    COALESCE(d.code, 'NO_DISCOUNT') AS discount_code,
    COALESCE(d.discount_percent, 0) AS discount_percent,
    ROUND((b.final_price * (COALESCE(d.discount_percent, 0) / 100)), 2) AS discount_amount,
    ROUND((b.final_price - (b.final_price * (COALESCE(d.discount_percent, 0) / 100))), 2) AS payable_amount,
    p.payment_id,
    p.amount AS paid_amount,
    p.status,
    ROUND((p.amount - (b.final_price - (b.final_price * (COALESCE(d.discount_percent, 0) / 100)))), 2) AS amount_difference
FROM bookings b
INNER JOIN users u ON b.user_id = u.user_id
INNER JOIN hotel h ON b.hotel_id = h.hotel_id
LEFT JOIN booking_discount bd ON b.booking_id = bd.booking_id
LEFT JOIN discounts d ON bd.discount_id = d.discount_id
LEFT JOIN payments p ON b.booking_id = p.booking_id
WHERE b.booking_id = 1;
