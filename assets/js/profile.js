document.addEventListener("DOMContentLoaded", function () {

    document.getElementById("bookingForm").addEventListener("submit", function (e) {
        e.preventDefault();

        const fullname = document.getElementById("fullname").value;
        const phone = document.getElementById("phone").value;
        const email = document.getElementById("email").value;
        const cccd = document.getElementById("cccd").value;
        const guests = document.getElementById("guests").value;

        localStorage.setItem("customer_fullname", fullname);
        localStorage.setItem("customer_phone", phone);
        localStorage.setItem("customer_email", email);
        localStorage.setItem("customer_cccd", cccd);
        localStorage.setItem("customer_guests", guests);

        alert("Đặt phòng thành công! Chúng tôi sẽ liên hệ với bạn sớm nhất.");

        window.location.href = "../html/paymentHN1.html";
    });
});
