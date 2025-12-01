const sendBtn = document.getElementById("sendBtn");
const input = document.getElementById("chatInput");
const chatBox = document.getElementById("chatBox");

function addUserMsg(text) {
    const div = document.createElement("div");
    div.className = "message user";
    div.innerText = text;
    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
}

function addBotMsg(text) {
    const div = document.createElement("div");
    div.className = "message bot";
    div.innerText = text;
    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
}

async function sendMessage() {
    const text = input.value.trim();
    if (!text) return;

    addUserMsg(text);
    input.value = "";

    const res = await fetch("/customer/chatbot", {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({message: text})
    });

    const data = await res.json();

    if (data.reply) {
        addBotMsg(data.reply);
    }
}

sendBtn.addEventListener("click", sendMessage);
input.addEventListener("keydown", (e) => {
    if (e.key === "Enter") sendMessage();
});
