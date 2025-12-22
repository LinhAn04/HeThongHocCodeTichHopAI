document.addEventListener("DOMContentLoaded", () => {
    const lessonId = window.__LESSON_ID__;

    // ===== Quiz =====
    const btnSubmitQuiz = document.getElementById("btnSubmitQuiz");
    const btnRetryQuiz = document.getElementById("btnRetryQuiz");
    const quizSummary = document.getElementById("quizSummary");

    if (btnSubmitQuiz) {
        btnSubmitQuiz.addEventListener("click", async () => {
            const answers = {};
            document.querySelectorAll(".q-block").forEach((block, idx) => {
                const picked = block.querySelector(`input[name="q${idx}"]:checked`);
                if (picked) answers[idx] = parseInt(picked.value, 10);
            });

            const res = await fetch(`/lesson/${lessonId}/quiz/submit`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ answers })
            });

            const data = await res.json();
            if (!data.ok) {
                alert(data.message || "Submit failed");
                return;
            }

            // show per-question explanations
            data.details.forEach(d => {
                const panel = document.querySelector(`.q-result[data-qindex="${d.questionIndex}"]`);
                if (!panel) return;

                panel.classList.remove("hidden");

                const pickedText = (d.picked == null) ? "No answer" : `Option ${d.picked + 1}`;
                const correctText = `Option ${d.correctIndex + 1}`;

                const choicesHtml = d.choices.map(c => {
                    const cls = c.isCorrect ? "choice correct" : "choice wrong";
                    return `
            <div class="${cls}">
              <div class="choice-line">
                <b>${c.isCorrect ? "Correct" : "Wrong"}</b> — ${escapeHtml(c.text)}
              </div>
              <div class="choice-reason">${escapeHtml(c.reason || "")}</div>
            </div>
          `;
                }).join("");

                panel.innerHTML = `
          <div class="q-meta">
            <span class="tag ${d.isCorrect ? "ok" : "bad"}">${d.isCorrect ? "Correct" : "Incorrect"}</span>
            <span class="meta">Your pick: ${pickedText}</span>
            <span class="meta">Correct: ${correctText}</span>
          </div>
          <div class="explain">${choicesHtml}</div>
        `;
            });

            quizSummary.classList.remove("hidden");
            quizSummary.innerHTML = `
        <b>Score:</b> ${Math.round(data.score)}% (${data.correct}/${data.total})<br>
        <b>Result:</b> ${data.passed ? "PASSED ✅" : "NOT PASSED ❌"}<br>
        ${data.saved ? "<span class='saved'>Progress saved.</span>" : "<span class='saved'>Trial mode: not saved.</span>"}
      `;
        });
    }

    if (btnRetryQuiz) {
        btnRetryQuiz.addEventListener("click", () => {
            document.querySelectorAll(".q-result").forEach(x => {
                x.classList.add("hidden");
                x.innerHTML = "";
            });
            quizSummary?.classList.add("hidden");
            quizSummary && (quizSummary.innerHTML = "");
            document.querySelectorAll(".quiz-form input[type=radio]").forEach(r => r.checked = false);
        });
    }

    // ===== Code =====
    const btnToggleHint = document.getElementById("btnToggleHint");
    const hintContent = document.getElementById("hintContent");
    if (btnToggleHint && hintContent) {
        btnToggleHint.addEventListener("click", () => {
            hintContent.classList.toggle("hidden");
            btnToggleHint.textContent = hintContent.classList.contains("hidden") ? "Show hint" : "Hide hint";
        });
    }

    const btnSubmitCode = document.getElementById("btnSubmitCode");
    const userOutput = document.getElementById("userOutput");
    const codeResult = document.getElementById("codeResult");

    if (btnSubmitCode && userOutput) {
        btnSubmitCode.addEventListener("click", async () => {
            const res = await fetch(`/lesson/${lessonId}/code/submit`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ userOutput: userOutput.value })
            });

            const data = await res.json();
            if (!data.ok) {
                alert(data.message || "Submit failed");
                return;
            }

            codeResult.classList.remove("hidden");
            codeResult.textContent = data.message + (data.saved ? " (saved)" : " (trial: not saved)");
            codeResult.classList.toggle("ok", !!data.passed);
            codeResult.classList.toggle("bad", !data.passed);
        });
    }

    function escapeHtml(s) {
        return String(s).replace(/[&<>"']/g, m => ({
            "&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#039;"
        }[m]));
    }
});
