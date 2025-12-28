document.addEventListener("DOMContentLoaded", () => {
    const typeSelect = document.getElementById("lessonType");
    const fragments = document.querySelectorAll(".lesson-fragment");

    function updateFragments() {
        const selectedType = typeSelect.value;

        fragments.forEach(f => {
            if (f.dataset.type === selectedType) {
                f.style.display = "block";
            } else {
                f.style.display = "none";
            }
        });
    }

    if (typeSelect) {
        updateFragments();
        typeSelect.addEventListener("change", updateFragments);
    }
});
