document.addEventListener('DOMContentLoaded', function() {
    const minRange = document.getElementById('minRange');
    const maxRange = document.getElementById('maxRange');
    const priceRangeText = document.getElementById('priceRangeText');

    if (minRange && maxRange && priceRangeText) {
        function updatePriceDisplay() {
            let minVal = parseInt(minRange.value);
            let maxVal = parseInt(maxRange.value);

            if (minVal > maxVal - 100000) {
                minVal = maxVal - 100000;
                minRange.value = minVal;
            }

            const maxText = maxVal >= 20000000 ? '20.000.000đ+' : formatPrice(maxVal) + 'đ';
            priceRangeText.textContent = `${formatPrice(minVal)}đ - ${maxText}`;
        }

        minRange.addEventListener('input', updatePriceDisplay);
        maxRange.addEventListener('input', updatePriceDisplay);
        updatePriceDisplay();
    }
    const searchForm = document.getElementById('searchForm');
    const filterForm = document.getElementById('filterForm');

    if (searchForm && filterForm) {
        searchForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const existingHiddens = searchForm.querySelectorAll('input[type="hidden"]');
            existingHiddens.forEach(input => {
                if (input.name !== 'cityId') {
                    input.remove();
                }
            });
            const minPriceInput = filterForm.querySelector('input[name="minPrice"]');
            if (minPriceInput && minPriceInput.value) {
                const hidden = document.createElement('input');
                hidden.type = 'hidden';
                hidden.name = 'minPrice';
                hidden.value = minPriceInput.value;
                searchForm.appendChild(hidden);
            }
            const maxPriceInput = filterForm.querySelector('input[name="maxPrice"]');
            if (maxPriceInput && maxPriceInput.value) {
                const hidden = document.createElement('input');
                hidden.type = 'hidden';
                hidden.name = 'maxPrice';
                hidden.value = maxPriceInput.value;
                searchForm.appendChild(hidden);
            }
            const checkedBoxes = filterForm.querySelectorAll('input[type="checkbox"]:checked');
            checkedBoxes.forEach(checkbox => {
                const hidden = document.createElement('input');
                hidden.type = 'hidden';
                hidden.name = checkbox.name;
                hidden.value = checkbox.value;
                searchForm.appendChild(hidden);
            });

            console.log('🚀 Submitting with filters:', {
                minPrice: minPriceInput?.value,
                maxPrice: maxPriceInput?.value,
                checkedBoxes: checkedBoxes.length
            });
            searchForm.submit();
        });
    }
    const listViewBtn = document.getElementById('listViewBtn');
    const gridViewBtn = document.getElementById('gridViewBtn');
    const gridContainer = document.getElementById('grid');

    if (listViewBtn && gridViewBtn && gridContainer) {
        listViewBtn.addEventListener('click', function() {
            switchToListView();
            sessionStorage.setItem('viewMode', 'list');
        });

        gridViewBtn.addEventListener('click', function() {
            switchToGridView();
            sessionStorage.setItem('viewMode', 'grid');
        });

        const savedView = sessionStorage.getItem('viewMode');
        if (savedView === 'grid') {
            switchToGridView();
        } else {
            switchToListView();
        }
    }
    const userMenu = document.querySelector('.user-menu');
    if (userMenu) {
        const userIcon = userMenu.querySelector('.nav-user-icon');

        if (userIcon) {
            userIcon.addEventListener('click', function(e) {
                e.preventDefault();
                e.stopPropagation();
                userMenu.classList.toggle('active');
            });
        }

        document.addEventListener('click', function(e) {
            if (!userMenu.contains(e.target)) {
                userMenu.classList.remove('active');
            }
        });
    }
    const checkinInput = document.querySelector('input[name="checkin"]');
    const checkoutInput = document.querySelector('input[name="checkout"]');

    if (checkinInput && checkoutInput) {
        const today = new Date().toISOString().split('T')[0];

        if (!checkinInput.value) {
            checkinInput.setAttribute('min', today);
        }

        checkinInput.addEventListener('change', function() {
            const checkinDate = new Date(this.value);
            checkinDate.setDate(checkinDate.getDate() + 1);
            const minCheckout = checkinDate.toISOString().split('T')[0];
            checkoutInput.setAttribute('min', minCheckout);

            if (checkoutInput.value && checkoutInput.value < minCheckout) {
                checkoutInput.value = '';
            }
        });
    }
    const searchLocationInput = document.getElementById('searchLocationInput');

    if (searchLocationInput && searchForm) {
        const originalLocation = searchLocationInput.value.trim().toLowerCase();
        searchLocationInput.addEventListener('input', function() {
            const hiddenCityId = searchForm.querySelector('input[name="cityId"]');
            if (hiddenCityId) {
                hiddenCityId.remove();
                console.log('🗑️ Removed cityId - user is changing location');
            }
        });
        searchForm.addEventListener('submit', function(e) {
            const newLocation = searchLocationInput.value.trim().toLowerCase();

            if (newLocation !== originalLocation) {
                const hiddenCityId = searchForm.querySelector('input[name="cityId"]');
                if (hiddenCityId) {
                    hiddenCityId.remove();
                    console.log('🗑️ Removed cityId on submit - location changed');
                }
            }
        });
    }
});
function switchToListView() {
    const gridContainer = document.getElementById('grid');
    const listViewBtn = document.getElementById('listViewBtn');
    const gridViewBtn = document.getElementById('gridViewBtn');

    if (!gridContainer || !listViewBtn || !gridViewBtn) return;

    gridContainer.classList.remove('grid-mode');
    gridContainer.classList.add('list-mode');
    listViewBtn.classList.add('active');
    gridViewBtn.classList.remove('active');

    document.querySelectorAll('.card-list').forEach(card => {
        card.style.display = 'flex';
    });
    document.querySelectorAll('.card-grid').forEach(card => {
        card.style.display = 'none';
    });
}

function switchToGridView() {
    const gridContainer = document.getElementById('grid');
    const listViewBtn = document.getElementById('listViewBtn');
    const gridViewBtn = document.getElementById('gridViewBtn');

    if (!gridContainer || !listViewBtn || !gridViewBtn) return;

    gridContainer.classList.remove('list-mode');
    gridContainer.classList.add('grid-mode');
    gridViewBtn.classList.add('active');
    listViewBtn.classList.remove('active');

    document.querySelectorAll('.card-list').forEach(card => {
        card.style.display = 'none';
    });
    document.querySelectorAll('.card-grid').forEach(card => {
        card.style.display = 'block';
    });
}

function formatPrice(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
}
