document.addEventListener("click", function (e) {
    const btn = e.target.closest(".output-tab");
    if (!btn) return;

    const tab = btn.dataset.tab;
    if (!tab) return;

    document.querySelectorAll(".output-tab")
        .forEach(b => b.classList.remove("active"));

    document.querySelectorAll(".output-panel")
        .forEach(p => p.classList.remove("active"));

    btn.classList.add("active");

    const panel = document.getElementById(tab);
    if (panel) panel.classList.add("active");
});

async function runCode() {
    const editor = document.getElementById("codeEditor");
    const consoleOutput = document.getElementById("consoleOutput");

    if (!editor || !consoleOutput) return;

    const code = editor.value.trim();
    if (!code) {
        consoleOutput.textContent = "No code to run.";
        return;
    }

    // switch to console tab safely
    const consoleTab = document.querySelector('[data-tab="console"]');
    if (consoleTab) consoleTab.click();

    consoleOutput.textContent = "⏳ Running code...\n";

    try {
        const res = await fetch("/api/code/run", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                code: code,
                input: "" // sau này cho stdin
            })
        });

        const data = await res.json();

        if (data.output) {
            consoleOutput.textContent = data.output;
        } else {
            consoleOutput.textContent = data.error || "Runtime error";
        }

    } catch (err) {
        consoleOutput.textContent = "Cannot connect to server.";
        console.error(err);
    }
}


async function submitCode() {
    const editor = document.getElementById("codeEditor");
    const testResult = document.getElementById("testcaseResult");

    if (!editor || !testResult) return;

    const code = editor.value.trim();
    if (!code) return;

    // switch tab safely
    const testTab = document.querySelector('[data-tab="testcases"]');
    if (testTab) testTab.click();

    testResult.innerHTML = "⏳ Submitting code...";

    // get problem safely
    const problemEl = document.querySelector("#problemCard pre");
    const problem = problemEl ? problemEl.innerText : "";

    try {
        // call AI evaluation
        const res = await fetch("/api/chatbot/code-evaluate", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                problem: problem,
                expected: "See sample output",
                code: code
            })
        });

        const data = await res.json();

        // fake test case result (tạm thời)
        testResult.innerHTML = `
            ✔ Test case 1 passed<br>
            ✔ Test case 2 passed
        `;

        openAIReview(data);

    } catch (err) {
        testResult.innerHTML = "❌ Submit failed.";
        console.error(err);
    }
}

function openAIReview(data) {
    const overlay = document.getElementById("aiReviewOverlay");
    if (!overlay) return;

    overlay.classList.remove("hidden");

    document.getElementById("aiScore").textContent =
        data.score ?? "--";

    document.getElementById("aiEvaluation").textContent =
        data.evaluation ?? "No evaluation.";

    const ul = document.getElementById("aiImprovements");
    ul.innerHTML = "";

    if (Array.isArray(data.improvements)) {
        data.improvements.forEach(i => {
            const li = document.createElement("li");
            li.textContent = i;
            ul.appendChild(li);
        });
    }
}

function closeAIReview() {
    const overlay = document.getElementById("aiReviewOverlay");
    if (overlay) overlay.classList.add("hidden");
}
