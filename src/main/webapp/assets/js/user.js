document.addEventListener('DOMContentLoaded', function() {
    const btnEdit = document.getElementById('btn-edit');
    const btnSave = document.getElementById('btn-save');
    const editableInputs = document.querySelectorAll('.editable-input');
    const fileInput = document.getElementById('file-upload');
    const phoneInput = document.getElementById('phone');
    const phoneError = document.getElementById('phone-error');

    let isPhoneValid = true;

    if (btnEdit && btnSave) {
        btnEdit.addEventListener('click', function() {
            editableInputs.forEach(input => {
                input.removeAttribute('disabled');
            });

            if(fileInput) {
                fileInput.removeAttribute('disabled');
            }

            btnSave.removeAttribute('disabled');
            btnEdit.classList.add('hidden-btn');

            if(editableInputs.length > 0) editableInputs[0].focus();
        });

        btnSave.addEventListener('click', function(event) {
            if (!isPhoneValid) {
                alert("Vui lòng nhập đúng định dạng số điện thoại!");
                return;
            }

            editableInputs.forEach(input => {
                input.setAttribute('disabled', true);
            });
            if(fileInput) {
                fileInput.setAttribute('disabled', true);
            }

            btnSave.setAttribute('disabled', true);
            btnEdit.classList.remove('hidden-btn');

            alert('Thông tin đã được cập nhật!');
        });
    }

    if (phoneInput) {
        phoneInput.addEventListener('input', function() {
            const value = this.value;
            const hasNonNumber = /\D/.test(value);

            if (hasNonNumber) {
                isPhoneValid = false;
                phoneError.style.display = 'inline';
                this.classList.add('input-error');
                btnSave.setAttribute('disabled', true);
                btnSave.style.opacity = '0.5';
                btnSave.style.cursor = 'not-allowed';
            } else {
                isPhoneValid = true;
                phoneError.style.display = 'none';
                this.classList.remove('input-error');

                if (btnEdit.classList.contains('hidden-btn')) {
                    btnSave.removeAttribute('disabled');
                    btnSave.style.opacity = '1';
                    btnSave.style.cursor = 'pointer';
                }
            }
        });
    }
});