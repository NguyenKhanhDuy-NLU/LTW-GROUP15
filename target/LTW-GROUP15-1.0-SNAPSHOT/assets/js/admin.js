function getCurrentHotel() {
    if (document.getElementById("hotel1").checked) return 1;
    if (document.getElementById("hotel2").checked) return 2;
    return 3;
}

const menuToSection = {
    "Dashboard": "dashboard",
    "Đặt phòng": "bookings",
    "Phòng": "rooms",
    "Thông tin khách sạn": "informations"
};

let currentActiveMenu = "Dashboard";

function showSection(menuText) {
    const hotel = getCurrentHotel();
    const sectionName = menuToSection[menuText] || menuText.toLowerCase();

    document.querySelectorAll(".section").forEach(sec => sec.classList.remove("active"));


    const sectionId = `${sectionName}-${hotel}`;
    const section = document.getElementById(sectionId);
    if (section) {
        section.classList.add("active");
    }


    const pageTitle = document.getElementById("pageTitle");
    if (pageTitle) {
        pageTitle.innerHTML = menuText;
    }

    document.querySelectorAll(".sidebar .menu-item").forEach(item => {
        item.classList.remove("active");
        if (item.textContent.trim() === menuText.trim()) {
            item.classList.add("active");
        }
    });
}


document.querySelectorAll(".sidebar .menu-item").forEach(item => {
    item.addEventListener("click", function() {
        const menuText = this.textContent.trim();
        currentActiveMenu = menuText;
        showSection(menuText);
    });
});


document.querySelectorAll('input[name="hotel"]').forEach(radio => {
    radio.addEventListener('change', () => {
        showSection(currentActiveMenu);
    });
});


showSection("Dashboard");
