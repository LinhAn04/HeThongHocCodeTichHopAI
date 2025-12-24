const sendBtn = document.getElementById("sendBtn");
const input = document.getElementById("chatInput");
const chatBox = document.getElementById("chatBox");

function addUserMsg(text) {
    const div = document.createElement("div");
    div.className = "message user";
    div.textContent = text;
    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
}

function addBotMsg(text) {
    const div = document.createElement("div");
    div.className = "message bot";
    div.innerHTML = marked.parse(
        text.trim().replace(/\n{2,}/g, "\n\n")
    );
    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
}

window.addEventListener("load", () => {
    addBotMsg(
        "👋 Hi! I'm **Codemy AI**. How can I help you today?"
    );
});

async function sendMessage() {
    const text = input.value.trim();
    if (!text) return;

    addUserMsg(text);
    input.value = "";

    // typing indicator
    const typingDiv = document.createElement("div");
    typingDiv.className = "message bot";
    typingDiv.textContent = "Codemy AI is typing...";
    chatBox.appendChild(typingDiv);
    chatBox.scrollTop = chatBox.scrollHeight;

    try {
        const res = await fetch("/api/chatbot/chat", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({ message: text })
        });

        if (!res.ok) throw new Error("Server error");

        const data = await res.json();
        chatBox.removeChild(typingDiv);

        addBotMsg(data.reply || "No response from AI.");
    } catch (e) {
        chatBox.removeChild(typingDiv);
        addBotMsg("Cannot connect to AI server.");
        console.error(e);
    }
}

sendBtn.addEventListener("click", sendMessage);
input.addEventListener("keydown", (e) => {
    if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault();
        sendMessage();
    }
});

input.addEventListener("input", () => {
    input.style.height = "auto";
    input.style.height = Math.min(input.scrollHeight, 160) + "px";
});

