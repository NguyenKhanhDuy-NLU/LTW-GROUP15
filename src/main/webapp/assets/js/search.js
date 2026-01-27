document.addEventListener('DOMContentLoaded', function () {

    const searchForm = document.getElementById('searchForm');
    const filterForm = document.getElementById('filterForm');
    const minRange = document.getElementById('minRange');
    const maxRange = document.getElementById('maxRange');
    const priceRangeText = document.getElementById('priceRangeText');

    function formatPrice(price) {
        return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    }

    function updatePriceDisplay() {
        if (!minRange || !maxRange) return;

        let minVal = parseInt(minRange.value);
        let maxVal = parseInt(maxRange.value);

        if (minVal > maxVal - 100000) {
            minVal = maxVal - 100000;
            minRange.value = minVal;
        }

        const maxText = maxVal >= 20000000
            ? '20.000.000đ+'
            : formatPrice(maxVal) + 'đ';

        priceRangeText.textContent = `${formatPrice(minVal)}đ - ${maxText}`;
    }

    if (minRange && maxRange) {
        minRange.addEventListener('input', updatePriceDisplay);
        maxRange.addEventListener('input', updatePriceDisplay);
        updatePriceDisplay();
    }

    if (filterForm) {
        if (minRange) {
            minRange.addEventListener('change', () => filterForm.submit());
        }
        if (maxRange) {
            maxRange.addEventListener('change', () => filterForm.submit());
        }

        filterForm.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
            checkbox.addEventListener('change', () => filterForm.submit());
        });
    }

    if (searchForm && filterForm) {
        searchForm.addEventListener('submit', function (e) {
            e.preventDefault();

            searchForm.querySelectorAll('input.hidden-filter').forEach(i => i.remove());

            copyFilterToSearch('minPrice');
            copyFilterToSearch('maxPrice');

            filterForm.querySelectorAll('input[type="checkbox"]:checked').forEach(cb => {
                appendHidden(cb.name, cb.value);
            });

            searchForm.submit();
        });

        searchForm.querySelectorAll('input[type="text"], input[type="date"]').forEach(input => {
            input.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    // Trigger form submit event
                    searchForm.dispatchEvent(new Event('submit', { cancelable: true }));
                }
            });
        });
    }

    const listViewBtn = document.getElementById('listViewBtn');
    const gridViewBtn = document.getElementById('gridViewBtn');
    const gridContainer = document.getElementById('grid');

    if (listViewBtn && gridViewBtn && gridContainer) {
        listViewBtn.addEventListener('click', () => {
            gridContainer.classList.remove('grid-mode');
            gridContainer.classList.add('list-mode');
            listViewBtn.classList.add('active');
            gridViewBtn.classList.remove('active');
            document.querySelectorAll('.card-list').forEach(c => c.style.display = 'flex');
            document.querySelectorAll('.card-grid').forEach(c => c.style.display = 'none');
        });

        gridViewBtn.addEventListener('click', () => {
            gridContainer.classList.remove('list-mode');
            gridContainer.classList.add('grid-mode');
            gridViewBtn.classList.add('active');
            listViewBtn.classList.remove('active');
            document.querySelectorAll('.card-list').forEach(c => c.style.display = 'none');
            document.querySelectorAll('.card-grid').forEach(c => c.style.display = 'block');
        });
    }
    function appendHidden(name, value) {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = name;
        input.value = value;
        input.classList.add('hidden-filter');
        searchForm.appendChild(input);
    }

    function copyFilterToSearch(name) {
        const input = filterForm.querySelector(`input[name="${name}"]`);
        if (input) {
            appendHidden(name, input.value);
        }
    }
});
