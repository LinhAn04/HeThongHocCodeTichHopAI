// Detect form changes
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

// Avatar Upload
const avatarClickArea = document.getElementById("avatarClickArea");
const avatarInput = document.getElementById("avatarInput");
const avatarImg = document.getElementById("avatarImg");

// Click avatar -> open file dialog
avatarClickArea.addEventListener("click", () => {
    avatarInput.click();
});

// When user selects file -> upload instantly
avatarInput.addEventListener("change", () => {
    const file = avatarInput.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    fetch("/customer/avatar", {
        method: "POST",
        body: formData
    })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                avatarImg.src = data.avatar; // update preview
            } else {
                alert("Upload failed: " + data.error);
            }
        })
        .catch(err => console.error(err));
});

// Toggle password visibility
document.getElementById("togglePassword")?.addEventListener("click", function() {
    const pwd = document.getElementById("password");
    if (pwd.type === "password") {
        pwd.type = "text";
        this.textContent = "Hide";
    } else {
        pwd.type = "password";
        this.textContent = "View";
    }
});