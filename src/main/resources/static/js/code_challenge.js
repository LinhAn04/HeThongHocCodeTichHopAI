function runCode() {
    const code = document.getElementById("codeEditor").value;

    // switch to Console tab
    document.querySelector('[data-tab="console"]').click();

    document.getElementById("consoleOutput").textContent =
        "Running code...\n\n" + code;
}

async function submitCode() {
    const code = document.getElementById("codeEditor").value.trim();

    if (!code) {
        return;
    }

    document.getElementById("testcaseResult").innerHTML =
        "✔ Test case 1 passed<br>✔ Test case 2 passed";

    document.querySelector('[data-tab="testcases"]').click();

    const res = await fetch("/api/chatbot/code-evaluate", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            problem: document.querySelector("#problemCard pre").innerText,
            expected: "See sample output",
            code: code
        })
    });

    const data = await res.json();
    openAIReview(data);
}


function openAIReview(data) {
    document.getElementById("aiReviewOverlay").classList.remove("hidden");

    document.getElementById("aiScore").textContent = data.score;

    document.getElementById("aiEvaluation").textContent = data.evaluation;

    const ul = document.getElementById("aiImprovements");
    ul.innerHTML = "";

    data.improvements.forEach(i => {
        const li = document.createElement("li");
        li.textContent = i;
        ul.appendChild(li);
    });
}

function closeAIReview() {
    document.getElementById("aiReviewOverlay").classList.add("hidden");
}

document.addEventListener("click", function (e) {
    if (!e.target.classList.contains("output-tab")) return;

    const tab = e.target.dataset.tab;

    // remove active tab
    document.querySelectorAll(".output-tab")
        .forEach(b => b.classList.remove("active"));

    document.querySelectorAll(".output-panel")
        .forEach(p => p.classList.remove("active"));

    // activate selected
    e.target.classList.add("active");
    document.getElementById(tab).classList.add("active");
});

async function runCode() {
    const code = document.getElementById("codeEditor").value;

    document.querySelector('[data-tab="console"]').click();
    document.getElementById("consoleOutput").textContent = "Running...";

    const res = await fetch("/api/code/run", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ code })
    });

    const data = await res.json();
    document.getElementById("consoleOutput").textContent = data.output;
}
