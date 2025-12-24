function runCode() {
    const code = document.getElementById("codeEditor").value;
    document.getElementById("consoleOutput").textContent =
        "Running code...\n" + code;
}

async function submitCode() {
    const code = document.getElementById("codeEditor").value;

    // fake test case result
    document.getElementById("testcaseResult").innerHTML =
        "✔ Test case 1 passed<br>✔ Test case 2 passed";

    // call AI review
    const res = await fetch("/api/chatbot/code-evaluate", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            problem: "Solve the coding challenge",
            expected: "Correct output",
            code: code
        })
    });

    const data = await res.json();

    openAIReview(data.feedback);
}

function openAIReview(feedback) {
    document.getElementById("aiReviewOverlay").classList.remove("hidden");

    // simple parsing (you can improve later)
    document.getElementById("aiScore").textContent =
        Math.floor(Math.random() * 30) + 70;

    document.getElementById("aiEvaluation").textContent = feedback;

    const ul = document.getElementById("aiImprovements");
    ul.innerHTML = "";
    ["Improve naming", "Handle edge cases", "Optimize logic"]
        .forEach(i => {
            const li = document.createElement("li");
            li.textContent = i;
            ul.appendChild(li);
        });
}

function closeAIReview() {
    document.getElementById("aiReviewOverlay").classList.add("hidden");
}

