const loginButton = document.querySelector('.nav-login-btn');
const modalOverlay = document.querySelector('.modal-overlay');
const modalCloseBtn = document.querySelector('.modal-close-btn');
const toggleLinks = document.querySelectorAll('.form-toggle-link');
const allForms = document.querySelectorAll('.modal-form');

loginButton.addEventListener('click', function(event) {
    event.preventDefault();
    modalOverlay.classList.remove('hidden');

    showForm('login-form');
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
    });
});