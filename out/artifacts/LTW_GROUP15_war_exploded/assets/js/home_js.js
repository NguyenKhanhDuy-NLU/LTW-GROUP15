document.addEventListener('DOMContentLoaded', function () {

    function setupActiveToggle(buttonSelector) {
        const allButtons = document.querySelectorAll(buttonSelector);

        if (allButtons.length === 0) return;

        allButtons.forEach(button => {
            button.addEventListener('click', function (event) {
                event.preventDefault();
                allButtons.forEach(btn => btn.classList.remove('active'));
                this.classList.add('active');
            });
        });
    }

    setupActiveToggle('.search-tabs .tab-btn');
});
