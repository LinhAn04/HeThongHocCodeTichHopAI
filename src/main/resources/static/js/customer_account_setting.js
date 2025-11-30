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

            saveBtn.disabled = !changed;
            saveBtn.classList.toggle("enabled", changed);
        });
    });
});

// avatar upload
const avatarClickArea = document.getElementById("avatarClickArea");
const avatarInput = document.getElementById("avatarInput");
const avatarImg = document.getElementById("avatarImg");

avatarClickArea.addEventListener("click", () => avatarInput.click());

avatarInput.addEventListener("change", () => {
    const file = avatarInput.files[0];
    if (!file) return;

    const previewUrl = URL.createObjectURL(file);
    avatarImg.src = previewUrl;

    const formData = new FormData();
    formData.append("file", file);

    fetch("/customer/avatar", {
        method: "POST",
        body: formData
    })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                showToast("Avatar updated!");
            } else {
                showToast("Upload failed!", "error");
            }
        });
});

// Invalid data for phone number
const phoneInput = document.getElementById("phone");
phoneInput.addEventListener("input", () => {
    phoneInput.value = phoneInput.value.replace(/[^0-9]/g, "");
});

// Validate
function showError(id, message) {
    const input = document.getElementById(id);
    input.classList.add("input-error");

    const old = input.parentElement.querySelector(".error-text");
    if (old) old.remove();

    const error = document.createElement("div");
    error.className = "error-text";
    error.innerText = message;
    input.parentElement.appendChild(error);
}

function clearError(id) {
    const input = document.getElementById(id);
    input.classList.remove("input-error");

    const msg = input.parentElement.querySelector(".error-text");
    if (msg) msg.remove();
}

// Validate content
function validateForm() {
    let ok = true;

    ["fullName", "phone", "birthday"].forEach(clearError);

    const fullname = document.getElementById("fullName").value.trim();
    if (fullname === "") {
        showError("fullName", "Fullname cannot be empty");
        ok = false;
    }

    const phone = phoneInput.value.trim();
    if (phone.length < 9 || phone.length > 11) {
        showError("phone", "Phone must be 9–11 digits");
        ok = false;
    }

    const birthday = document.getElementById("birthday").value;
    if (birthday) {
        const d = new Date(birthday);
        const today = new Date();
        today.setHours(0,0,0,0);

        if (d > today) {
            showError("birthday", "Date of birth cannot be in the future");
            ok = false;
        }
    }

    return ok;
}

["fullName", "phone", "birthday"].forEach(id => {
    document.getElementById(id).addEventListener("input", () => clearError(id));
});

// Submit
form.addEventListener("submit", function (e) {
    e.preventDefault();

    if (!validateForm()) return;

    const payload = getFormData();

    fetch("/customer/update-profile", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                showToast("Profile updated successfully!");
                saveBtn.disabled = true;
                saveBtn.classList.remove("enabled");
                initialData = getFormData();
            } else {
                showToast("Update failed!", "error");
            }
        });
});

// toast
function showToast(message, type = "success") {
    const toast = document.getElementById("toast");
    const blur = document.querySelector(".toast-blur");

    toast.textContent = message;

    toast.classList.remove("error");
    if (type === "error") toast.classList.add("error");

    toast.classList.add("show");
    blur.classList.add("show");

    setTimeout(() => {
        toast.classList.remove("show");
        blur.classList.remove("show");
    }, 3000);
}
