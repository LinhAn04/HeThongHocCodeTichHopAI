document.addEventListener("DOMContentLoaded", () => {
    const locked = document.body.dataset.locked === "true";

    if (locked) {
        const codeFields = document.querySelectorAll(
            "textarea[name='starterCode'], textarea[name='codeDeBai']"
        );

        codeFields.forEach(f => f.disabled = true);
    }
});
