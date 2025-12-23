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

// chatbot for enroll courses
function openChatbot() {
    document.getElementById("chatbotOverlay")
        ?.classList.remove("hidden");
}

function closeChatbot() {
    document.getElementById("chatbotOverlay")
        ?.classList.add("hidden");
}

/* auto open if from lesson */
document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    if (params.get("openChatbot") === "1") {
        openChatbot();
    }
});

// Shift enter để xuống dòng
chatInput.addEventListener("keydown", e => {
    if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault();
        sendMessage();
    }
});

