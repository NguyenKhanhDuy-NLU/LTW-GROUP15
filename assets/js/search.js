const listViewBtn = document.getElementById("listViewBtn");
const gridViewBtn = document.getElementById("gridViewBtn");
const gridContainer = document.getElementById("grid");

listViewBtn.addEventListener("click", (e) => {
  e.stopPropagation();
  gridContainer.classList.add("list-mode");
  gridContainer.classList.remove("grid-mode");
  listViewBtn.classList.add("active");
  gridViewBtn.classList.remove("active");
});

gridViewBtn.addEventListener("click", (e) => {
  e.stopPropagation();
  gridContainer.classList.add("grid-mode");
  gridContainer.classList.remove("list-mode");
  gridViewBtn.classList.add("active");
  listViewBtn.classList.remove("active");
});

const minRange = document.getElementById("minRange");
const maxRange = document.getElementById("maxRange");

if (minRange && maxRange) {
  const gap = 100000;

  minRange.addEventListener("input", () => {
    if (parseInt(minRange.value) >= parseInt(maxRange.value) - gap) {
      minRange.value = parseInt(maxRange.value) - gap;
    }
    updateSliderTrack();
  });

  maxRange.addEventListener("input", () => {
    if (parseInt(maxRange.value) <= parseInt(minRange.value) + gap) {
      maxRange.value = parseInt(minRange.value) + gap;
    }
    updateSliderTrack();
  });

  function updateSliderTrack() {
    const minVal = parseInt(minRange.value);
    const maxVal = parseInt(maxRange.value);
    const minPercent = ((minVal - minRange.min) / (minRange.max - minRange.min)) * 100;
    const maxPercent = ((maxVal - maxRange.max) / (maxRange.max - maxRange.min)) * 100;

    document.querySelector(".slider-track").style.background = `linear-gradient(to right, 
      #ddd ${minPercent}%, 
      #0078ff ${minPercent}%, 
      #0078ff ${maxPercent}%, 
      #ddd ${maxPercent}%)`;
  }

  updateSliderTrack();
  minRange.dispatchEvent(new Event("input"));
}

const cityMaps = {
    "Hà Nội": { map: "mapHN.html", key: "Ha Noi" },
    "TP. Hồ Chí Minh": { map: "mapHCM.html", key: "Ho Chi Minh City" },
    "Hải Phòng": { map: "mapHP.html", key: "Hai Phong" },
    "Đà Nẵng": { map: "mapDN.html", key: "Da Nang" },
    "TP. Cần Thơ": { map: "mapCT.html", key: "Can Tho" },
    "Hạ Long": { map: "mapHL.html", key: "Ha Long" },
    "Hội An": { map: "mapHA.html", key: "Hoi An" },
    "Phú Quốc": { map: "mapPQ.html", key: "Phu Quoc" },
    "Nha Trang": { map: "mapNT.html", key: "Nha Trang" },
    "Vũng Tàu": { map: "mapVT.html", key: "Vung Tau" },
    "Huế": { map: "mapHUE.html", key: "Hue" }
};

const searchInput = document.getElementById("q");
const searchBtn = document.getElementById("searchBtn");
const mapBtn = document.querySelector(".map-icon");

// === TÌM KIẾM ===
searchBtn.addEventListener("click", handleSearch);
searchInput.addEventListener("keydown", (e) => {
  if (e.key === "Enter") handleSearch();
});

function handleSearch() {
  const query = searchInput.value.trim();
  if (!query) {
    showAllHotels();
    resetMapLink();
    return;
  }

  filterHotelsByCity(query);
  updateMapLink(query);
}

function filterHotelsByCity(cityName) {
  const cards = document.querySelectorAll(".card");
  const normalizedInput = removeAccents(cityName.toLowerCase());
  let hasMatch = false;
  let matchedKey = null;

  for (const [viName, info] of Object.entries(cityMaps)) {
    if (
      removeAccents(viName.toLowerCase()).includes(normalizedInput) ||
      removeAccents(info.key.toLowerCase()).includes(normalizedInput)
    ) {
      matchedKey = removeAccents(info.key.toLowerCase());
      break;
    }
  }

  cards.forEach(card => {
    const dataCity = removeAccents((card.getAttribute("data-city") || "").toLowerCase());
    if (matchedKey && dataCity.includes(matchedKey)) {
      card.style.display = "";
      hasMatch = true;
    } else {
      card.style.display = "none";
    }
  });

  if (!hasMatch) {
    resetMapLink();
  }
}

function updateMapLink(cityName) {
  const normalizedInput = removeAccents(cityName.toLowerCase());
  for (const [viName, info] of Object.entries(cityMaps)) {
    if (
      removeAccents(viName.toLowerCase()).includes(normalizedInput) ||
      removeAccents(info.key.toLowerCase()).includes(normalizedInput)
    ) {
      mapBtn.onclick = () => window.location.href = info.map;
      mapBtn.style.cursor = "pointer";
      mapBtn.title = `Xem bản đồ ${viName}`;
      mapBtn.classList.remove("no-city");
      return;
    }
  }
  resetMapLink();
}

function resetMapLink() {
  mapBtn.onclick = () => {
    mapBtn.classList.add("refreshing");
    setTimeout(() => window.location.href = "search.html", 300);
  };
  mapBtn.style.cursor = "pointer";
  mapBtn.title = "Chưa chọn tỉnh/thành — bấm để tải lại trang";
  mapBtn.classList.add("no-city");
}

function showAllHotels() {
  document.querySelectorAll(".card").forEach(card => {
    card.style.display = "";
  });
}

function removeAccents(str) {
  return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/đ/g, "d").replace(/Đ/g, "D");
}

document.addEventListener("DOMContentLoaded", () => {
  searchInput.value = "Hà Nội";

  handleSearch();

  resetMapLink();
  updateMapLink("Hà Nội");
});