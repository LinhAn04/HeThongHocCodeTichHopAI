const tabBtns = document.querySelectorAll(".tab-btn");
const panels = document.querySelectorAll(".tab-panel");

tabBtns.forEach(btn => {
    btn.addEventListener("click", e => {
        e.preventDefault();

        tabBtns.forEach(b => b.classList.remove("active"));
        panels.forEach(p => p.classList.remove("active"));

        btn.classList.add("active");
        const tab = btn.dataset.tab;
        document.getElementById(tab)?.classList.add("active");

        history.replaceState(null, "", "#" + tab);
    });
});

// Load tab from hash (NO SCROLL)
window.addEventListener("load", () => {
    let hash = window.location.hash.replace("#", "");

    // nếu không có hash → set mặc định là lessons (không scroll)
    if (!hash) {
        history.replaceState(null, "", "#lessons");
        hash = "lessons";
    }

    // active tab tương ứng (không scroll)
    const targetBtn = [...tabBtns].find(
        b => b.dataset.tab === hash
    );

    targetBtn?.click();
});

// require login popup
function openLoginPopup() {
    document.getElementById("popup")?.classList.remove("hidden");
}

function closeLoginPopup() {
    document.getElementById("popup")?.classList.add("hidden");
}

document.getElementById("closePopup")?.addEventListener("click", closeLoginPopup);
