const loginButton = document.querySelector('.nav-login-btn');
const modalOverlay = document.querySelector('.modal-overlay');
const modalCloseBtn = document.querySelector('.modal-close-btn');
const toggleLinks = document.querySelectorAll('.form-toggle-link');
const allForms = document.querySelectorAll('.modal-form');
const loginSubmitBtn = document.getElementById('btn-login-submit');
const loginEmail = document.getElementById('login-email');
const loginPass = document.getElementById('login-pass');
const loginError = document.getElementById('login-error');

loginButton.addEventListener('click', function(event) {
    event.preventDefault();
    modalOverlay.classList.remove('hidden');
    showForm('login-form');
    loginError.style.display = 'none';
});

modalCloseBtn.addEventListener('click', function() {
    modalOverlay.classList.add('hidden');
});

modalOverlay.addEventListener('click', function(event) {
    if (event.target === modalOverlay) {
        modalOverlay.classList.add('hidden');
    }
});

function showForm(formId) {
    allForms.forEach(form => {
        form.classList.add('hidden');
    });

    const formToShow = document.getElementById(formId);
    if (formToShow) {
        formToShow.classList.remove('hidden');
    }
}

toggleLinks.forEach(link => {
    link.addEventListener('click', function(event) {
        event.preventDefault();
        const targetFormId = this.getAttribute('data-target');
        showForm(targetFormId);
        loginError.style.display = 'none';
    });
});

function setupActiveToggle(buttonSelector) {
    const allButtons = document.querySelectorAll(buttonSelector);

    allButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();

            allButtons.forEach(btn => {
                btn.classList.remove('active');
            });

            this.classList.add('active');
        });
    });
}

setupActiveToggle('.search-tabs .tab-btn');
setupActiveToggle('.destination-filters .filter-btn');

const sendResetBtn = document.getElementById('btn-send-reset');

if (sendResetBtn) {
    sendResetBtn.addEventListener('click', function(event) {
        event.preventDefault();

        const forgotInput = document.getElementById('forgot-email');
        const inputValue = forgotInput.value.trim();

        if (!inputValue) {
            alert("Vui lòng nhập Email hoặc Số điện thoại!");
            forgotInput.focus();
            return;
        }

        alert(`Mã xác thực đã được gửi tới: ${inputValue}\nVui lòng kiểm tra hòm thư/tin nhắn.`);
        showForm('login-form');
    });
}

if (loginSubmitBtn) {
    loginSubmitBtn.addEventListener('click', function(e) {
        e.preventDefault();
        const emailVal = loginEmail.value.trim();
        const passVal = loginPass.value.trim();

        if (!emailVal || !passVal) {
            loginError.style.display = 'block';
        } else {
            loginError.style.display = 'none';
            window.location.href = 'assets/html/index_user.html';
        }
    });
}