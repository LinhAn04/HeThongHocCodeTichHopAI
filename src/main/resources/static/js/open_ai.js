document.getElementById("btnAskAI").onclick = async () => {
    const msg = aiInput.value;

    const res = await fetch("/api/ai/enroll-chat", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ message: msg })
    });

    const data = await res.json();
    aiMessages.innerHTML += `<div>${data.reply}</div>`;
};
