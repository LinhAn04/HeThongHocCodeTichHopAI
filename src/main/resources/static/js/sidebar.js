// Toggle expand / collapse sidebar
const sidebar = document.getElementById("sidebar");
const toggleBtn = document.getElementById("toggleSidebarBtn");

toggleBtn.addEventListener("click", () => {
    sidebar.classList.toggle("collapsed");

    // Toggle icon from << to >>
    toggleBtn.textContent = sidebar.classList.contains("collapsed") ? ">>" : "<<";
});

// Submenu open/close
document.querySelectorAll(".submenu-toggle").forEach(toggle => {
    toggle.addEventListener("click", (e) => {
        e.preventDefault();

        const parent = toggle.parentElement;
        parent.classList.toggle("open");
    });
});
