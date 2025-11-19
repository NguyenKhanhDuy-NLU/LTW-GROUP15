document.addEventListener("DOMContentLoaded", function () {

    document.getElementById("bookingForm").addEventListener("submit", function (e) {
        e.preventDefault();

        alert("Đặt phòng thành công! Chúng tôi sẽ liên hệ với bạn sớm nhất.");

        window.location.href = "../html/paymentHN1.html";
    });
});
