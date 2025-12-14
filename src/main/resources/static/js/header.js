document.addEventListener("DOMContentLoaded", () => {

    const notifyBtn = document.getElementById("notifyBtn");
    const notifyPopup = document.getElementById("notifyPopup");

    const avatarBtn = document.getElementById("avatarBtn");
    const avatarPopup = document.getElementById("avatarPopup");

    // Toggle notification popup
    notifyBtn?.addEventListener("click", (e) => {
        e.stopPropagation();
        notifyPopup?.classList.toggle("hidden");
        avatarPopup?.classList.add("hidden");
    });

    // Toggle avatar popup
    avatarBtn?.addEventListener("click", (e) => {
        e.stopPropagation();
        avatarPopup?.classList.toggle("hidden");
        notifyPopup?.classList.add("hidden");
    });

    // Click inside popups → không đóng
    avatarPopup?.addEventListener("click", e => e.stopPropagation());
    notifyPopup?.addEventListener("click", e => e.stopPropagation());

    // Click ngoài → đóng hết
    document.addEventListener("click", () => {
        avatarPopup?.classList.add("hidden");
        notifyPopup?.classList.add("hidden");
    });

});
