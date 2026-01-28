

function openCancelModal() {
    document.getElementById("cancel-modal").style.display = "flex";
}

function closeCancelModal() {
    const modal = document.getElementById("cancel-modal");
    modal.style.display = "none";

    document.querySelectorAll('input[name="reason"]').forEach(radio => {
        radio.checked = false;
    });

    const otherTextarea = document.getElementById("other-reason-text");
    otherTextarea.style.display = "none";
    otherTextarea.value = "";
}

const otherReasonRadio = document.getElementById("other-reason-radio");
if (otherReasonRadio) {
    otherReasonRadio.addEventListener("change", function() {
        const textarea = document.getElementById("other-reason-text");
        textarea.style.display = this.checked ? "block" : "none";
        if (!this.checked) {
            textarea.value = "";
        }
    });
}


function confirmCancel() {
    const selectedReason = document.querySelector('input[name="reason"]:checked');

    if (!selectedReason) {
        alert("Vui lòng chọn lý do hủy!");
        return;
    }

    let reason = selectedReason.value;
    let otherReason = "";


    if (reason === "Lý do khác") {
        otherReason = document.getElementById("other-reason-text").value.trim();

        if (!otherReason) {
            alert("Vui lòng nhập lý do khác!");
            return;
        }
    }

    fetch(contextPath + "/cancel-booking", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            bookingId: bookingId,
            reason: reason,
            otherReason: otherReason
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                closeCancelModal();
                showSuccessMessage();

                // Redirect after 2 seconds
                setTimeout(() => {
                    window.location.href = contextPath + "/";
                }, 2000);
            } else {
                alert(data.message || "Không thể hủy đặt phòng. Vui lòng thử lại!");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Có lỗi xảy ra khi hủy đặt phòng. Vui lòng thử lại!");
        });
}


function showSuccessMessage() {
    const message = document.getElementById("success-message");
    if (message) {
        message.style.display = "flex";
    }
}


function closeSuccessMessage() {
    const message = document.getElementById("success-message");
    if (message) {
        message.style.display = "none";
    }
}


window.addEventListener('click', function(event) {
    const modal = document.getElementById("cancel-modal");
    if (modal && event.target === modal) {
        closeCancelModal();
    }
});


document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeCancelModal();
    }
});