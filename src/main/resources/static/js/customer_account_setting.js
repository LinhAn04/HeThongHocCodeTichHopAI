// Detect unsaved changes
const form = document.getElementById("accountForm");
const saveBtn = document.getElementById("saveBtn");

let initialData = {};

function getFormData() {
    return {
        hoTen: document.getElementById("fullName").value,
        soDienThoai: document.getElementById("phone").value,
        ngaySinh: document.getElementById("birthday").value,
        gioiTinh: document.getElementById("gender").value,
        diaChi: document.getElementById("address").value
    };
}

// Track changes
window.addEventListener("load", () => {
    initialData = getFormData();

    form.querySelectorAll("input, select").forEach(el => {
        el.addEventListener("input", () => {
            const now = getFormData();
            const changed = JSON.stringify(initialData) !== JSON.stringify(now);

            if (changed) {
                saveBtn.classList.add("enabled");
                saveBtn.disabled = false;
            } else {
                saveBtn.classList.remove("enabled");
                saveBtn.disabled = true;
            }
        });
    });
});

// Avatar popup logic
const avatarBtn = document.getElementById("avatarEditBtn");
const avatarPopup = document.getElementById("avatarPopup");

avatarBtn.addEventListener("click", (e) => {
    e.stopPropagation();
    avatarPopup.classList.toggle("hidden");
});

document.addEventListener("click", (e) => {
    if (!avatarPopup.contains(e.target) && e.target !== avatarBtn) {
        avatarPopup.classList.add("hidden");
    }
});
