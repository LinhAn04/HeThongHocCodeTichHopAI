const tabBtns = document.querySelectorAll(".tab-btn");
const panels = document.querySelectorAll(".tab-panel");

tabBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        tabBtns.forEach(b => b.classList.remove("active"));
        panels.forEach(p => p.classList.remove("active"));

        btn.classList.add("active");
        const tab = btn.dataset.tab;
        document.getElementById(tab).classList.add("active");

        // update hash for better UX
        window.location.hash = tab;
    });
});

// If hash exists, open that tab
window.addEventListener("load", () => {
    const hash = window.location.hash.replace("#", "");
    if (!hash) return;
    const targetBtn = [...tabBtns].find(b => b.dataset.tab === hash);
    if (targetBtn) targetBtn.click();
});
