
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

    function showSection(menuText) {
        const hotel = getCurrentHotel();
        const sectionName = menuToSection[menuText] || menuText.toLowerCase();

        document.querySelectorAll(".section").forEach(sec => sec.classList.remove("active"));

        const sectionId = `${sectionName}-${hotel}`;
        const section = document.getElementById(sectionId);
        if (section) {
            section.classList.add("active");
        }

        const h2 = section ? section.querySelector("h2") : null;
        if (h2) {
            const titleText = h2.textContent.split("–")[0].trim();
            document.getElementById("pageTitle").innerHTML =
                (titleText.includes("Dashboard") ? '<i class="fas fa-tachometer-alt"></i> ' : '') +
                titleText;
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
            showSection(menuText);
        });
    });

    let currentActiveMenu = "Dashboard";

    document.querySelectorAll('input[name="hotel"]').forEach(radio => {
        radio.addEventListener('change', () => {
            showSection(currentActiveMenu);
        });
    });

    document.querySelectorAll(".sidebar .menu-item").forEach(item => {
        const text = item.textContent.trim();
        item.onclick = () => {
            currentActiveMenu = text;
            showSection(text);
        };
    });

    showSection("Dashboard");
