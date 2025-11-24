// SIDEBAR COLLAPSE TOGGLE
const sidebar = document.getElementById("sidebar");
const toggleBtn = document.getElementById("toggleSidebarBtn");

toggleBtn.addEventListener("click", () => {
    sidebar.classList.toggle("collapsed");
});

// SUBMENU OPEN/CLOSE
document.querySelectorAll(".menu-title .submenu-toggle").forEach(toggle => {
    toggle.addEventListener("click", (e) => {
        e.preventDefault();
        const parent = toggle.closest(".menu-title");
        parent.classList.toggle("open");
    });
});
