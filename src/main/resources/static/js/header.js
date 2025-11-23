const notifyBtn = document.getElementById("notifyBtn");
const notifyPopup = document.getElementById("notifyPopup");

const avatarBtn = document.getElementById("avatarBtn");
const avatarPopup = document.getElementById("avatarPopup");

// Toggle Notification popup
notifyBtn?.addEventListener("click", () => {
    notifyPopup.classList.toggle("hidden");
    avatarPopup.classList.add("hidden");
});

// Toggle Avatar popup
avatarBtn?.addEventListener("click", () => {
    avatarPopup.classList.toggle("hidden");
    notifyPopup.classList.add("hidden");
});

// Click outside to close
document.addEventListener("click", (event) => {
    if (!notifyBtn.contains(event.target) &&
        !notifyPopup.contains(event.target)) {
        notifyPopup.classList.add("hidden");
    }

    if (!avatarBtn.contains(event.target) &&
        !avatarPopup.contains(event.target)) {
        avatarPopup.classList.add("hidden");
    }
});
