const bookingCode = document.querySelector('.booking-code strong').textContent.trim();

function openCancelModal() {
    document.getElementById("cancel-modal").style.display = "flex";
    document.getElementById("booking-code-display").textContent = bookingCode;
}

function closeCancelModal() {
    document.getElementById("cancel-modal").style.display = "none";
    document.querySelectorAll('input[name="reason"]').forEach(r => r.checked = false);
    const other = document.getElementById("other-reason-text");
    other.style.display = "none";
    other.value = "";
}

document.getElementById("other-reason-radio").addEventListener("change", function () {
    const textarea = document.getElementById("other-reason-text");
    textarea.style.display = this.checked ? "block" : "none";
    if (!this.checked) textarea.value = "";
});

function confirmCancel() {
    const selected = document.querySelector('input[name="reason"]:checked');

    if (!selected) {
        alert("Vui lòng chọn lý do hủy!");
        return;
    }

    if (selected.value === "Lý do khác") {
        const otherText = document.getElementById("other-reason-text").value.trim();
        if (!otherText) {
            alert("Vui lòng nhập lý do khác!");
            return;
        }
    }

    closeCancelModal();

    const msg = document.getElementById("success-message");
    msg.style.display = "flex";
    setTimeout(() => {
        const redirectUrl = document.querySelector('.btn-danger').dataset.paymentUrl || "index.html";
        window.location.href = redirectUrl;
    }, 2000);
}

function closeSuccessMessage() {
    document.getElementById("success-message").style.display = "none";
}