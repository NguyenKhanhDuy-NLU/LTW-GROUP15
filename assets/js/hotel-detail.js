document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".rooms .btn");

    buttons.forEach(btn => {
        btn.addEventListener("click", function () {

            const roomName = this.dataset.name;
            const roomPrice = this.dataset.price;
            const roomImg = this.dataset.img;

            localStorage.setItem("selectedRoomName", roomName);
            localStorage.setItem("selectedRoomPrice", roomPrice);
            localStorage.setItem("selectedRoomImg", roomImg);

            window.location.href = "profile.html";
        });
    });
});
