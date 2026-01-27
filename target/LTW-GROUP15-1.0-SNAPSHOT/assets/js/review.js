
const contextPath = document.querySelector('script[src*="reviews.js"]')
    .src.split('/assets/')[0].replace(window.location.origin, '');

function openReviewModal() {
    document.getElementById('reviewModal').style.display = 'flex';
}

function closeReviewModal() {
    document.getElementById('reviewModal').style.display = 'none';
    document.getElementById('reviewForm').reset();
    document.querySelector('.char-count').textContent = '0 / 1000';
    document.getElementById('ratingError').textContent = '';
}

window.addEventListener('click', function(event) {
    const modal = document.getElementById('reviewModal');
    if (event.target === modal) {
        closeReviewModal();
    }
});
document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeReviewModal();
    }
});
const commentTextarea = document.getElementById('comment');
if (commentTextarea) {
    commentTextarea.addEventListener('input', function() {
        const charCount = this.value.length;
        document.querySelector('.char-count').textContent = charCount + ' / 1000';
    });
}

const starInputs = document.querySelectorAll('.star-rating input');
const starLabels = document.querySelectorAll('.star-rating label');

starLabels.forEach((label, index) => {
    label.addEventListener('mouseenter', function() {
        highlightStars(starLabels.length - index);
    });
});

document.querySelector('.star-rating').addEventListener('mouseleave', function() {
    const checkedInput = document.querySelector('.star-rating input:checked');
    if (checkedInput) {
        const checkedIndex = Array.from(starInputs).indexOf(checkedInput);
        highlightStars(starLabels.length - checkedIndex);
    } else {
        highlightStars(0);
    }
});

starInputs.forEach((input, index) => {
    input.addEventListener('change', function() {
        highlightStars(starLabels.length - index);
        document.getElementById('ratingError').textContent = '';
    });
});

function highlightStars(count) {
    starLabels.forEach((label, index) => {
        if (index < count) {
            label.classList.add('active');
        } else {
            label.classList.remove('active');
        }
    });
}

document.getElementById('reviewForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const formData = new FormData(this);
    const rating = formData.get('rating');
    const comment = formData.get('comment');
    if (!rating) {
        document.getElementById('ratingError').textContent = 'Vui lòng chọn số sao đánh giá';
        return;
    }
    if (!comment || comment.trim().length < 10) {
        alert('Nội dung đánh giá phải có ít nhất 10 ký tự');
        return;
    }

    const submitButton = this.querySelector('button[type="submit"]');
    submitButton.disabled = true;
    submitButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang gửi...';

    fetch(contextPath + '/create-review', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams(formData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.message);
                closeReviewModal();
                // Reload page to show new review
                window.location.reload();
            } else {
                alert(data.message);
                submitButton.disabled = false;
                submitButton.innerHTML = '<i class="fas fa-paper-plane"></i> Gửi đánh giá';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Có lỗi xảy ra. Vui lòng thử lại!');
            submitButton.disabled = false;
            submitButton.innerHTML = '<i class="fas fa-paper-plane"></i> Gửi đánh giá';
        });
});

document.addEventListener('DOMContentLoaded', function() {
    const userMenu = document.querySelector('.user-menu');

    if (userMenu) {
        const userIcon = userMenu.querySelector('.nav-user-icon');

        if (userIcon) {
            userIcon.addEventListener('click', function(e) {
                e.preventDefault();
                e.stopPropagation();
                userMenu.classList.toggle('active');
            });
        }

        document.addEventListener('click', function(e) {
            if (!userMenu.contains(e.target)) {
                userMenu.classList.remove('active');
            }
        });
    }
});