document.addEventListener("DOMContentLoaded", () => {
    const lessonId = window.__LESSON_ID__;

    /* Popup hiện ra nhắc nhở chưa chọn full đáp án */
    function showPopup() {
        document.getElementById("popup")?.classList.remove("hidden");
    }
    function hidePopup() {
        document.getElementById("popup")?.classList.add("hidden");
    }
    document.getElementById("closePopup")?.addEventListener("click", hidePopup);

    /* Quiz logic */
    const btnSubmitQuiz = document.getElementById("btnSubmitQuiz");
    const btnRetryQuiz = document.getElementById("btnRetryQuiz");
    const quizSummary = document.getElementById("quizSummary");

    // chọn đáp án -> highlight
    document.querySelectorAll(".opt input[type=radio]").forEach(radio => {
        radio.addEventListener("change", () => {
            const qIndex = radio.name.replace("q", "");
            document
                .querySelectorAll(`.opt[data-qindex="${qIndex}"]`)
                .forEach(o => o.classList.remove("selected"));

            radio.closest(".opt").classList.add("selected");
        });
    });

    if (btnSubmitQuiz) {
        btnSubmitQuiz.addEventListener("click", async () => {

            /* collect answers */
            const answers = {};
            document.querySelectorAll(".q-block").forEach((block, idx) => {
                const picked = block.querySelector(`input[name="q${idx}"]:checked`);
                if (picked) answers[idx] = parseInt(picked.value, 10);
            });

            /* check đủ câu chưa */
            const totalQuestions = document.querySelectorAll(".q-block").length;
            if (Object.keys(answers).length < totalQuestions) {
                showPopup();
                return;
            }

            /* submit */
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

            /* reset option state */
            document.querySelectorAll(".opt").forEach(o => {
                o.classList.remove("correct", "wrong");
            });

            /* render result */
            data.details.forEach(d => {
                const panel = document.querySelector(
                    `.q-result[data-qindex="${d.questionIndex}"]`
                );
                if (!panel) return;

                panel.classList.remove("hidden");
                panel.innerHTML = "";

                let wrongReason = "";

                // tô màu option
                d.choices.forEach(c => {
                    const optEl = document.querySelector(
                        `.opt[data-qindex="${d.questionIndex}"][data-oindex="${c.index}"]`
                    );
                    if (!optEl) return;

                    // đáp án đúng -> tô xanh
                    if (c.isCorrect) {
                        optEl.classList.add("correct");
                    }

                    // đáp án đã chọn nhưng sai -> tô đỏ + lấy lý do
                    if (d.picked === c.index && !c.isCorrect) {
                        optEl.classList.add("wrong");
                        wrongReason = c.reason || "";
                    }
                });

                // nếu trả lời sai -> render lý do câu đó sai
                if (!d.isCorrect && wrongReason) {
                    panel.innerHTML = `
                        <div class="q-meta">
                            <span class="tag bad">Incorrect</span>
                        </div>
            
                        <div class="choice-reason">
                            ${escapeHtml(wrongReason)}
                        </div>
                    `;
                }

                // nếu trả lời đúng -> chỉ tag xanh, không giải thích
                if (d.isCorrect) {
                    panel.innerHTML = `
                        <div class="q-meta">
                            <span class="tag ok">Correct</span>
                        </div>
                    `;
                }
            });

            /* summary */
            quizSummary.classList.remove("hidden");
            quizSummary.innerHTML = `
                <b>Score:</b> ${Math.round(data.score)}%
                (${data.correct}/${data.total})<br>
                <b>Result:</b> ${data.passed ? "PASSED" : "NOT PASSED"}
            `;
        });
    }

    /* retry quiz */
    if (btnRetryQuiz) {
        btnRetryQuiz.addEventListener("click", () => {
            document.querySelectorAll(".q-result").forEach(x => {
                x.classList.add("hidden");
                x.innerHTML = "";
            });

            quizSummary?.classList.add("hidden");
            if (quizSummary) quizSummary.innerHTML = "";

            document.querySelectorAll(".opt").forEach(o => {
                o.classList.remove("correct", "wrong", "selected");
            });

            document
                .querySelectorAll(".quiz-form input[type=radio]")
                .forEach(r => r.checked = false);
        });
    }

    /* CODE EXERCISE */
    const btnToggleHint = document.getElementById("btnToggleHint");
    const hintContent = document.getElementById("hintContent");

    if (btnToggleHint && hintContent) {
        btnToggleHint.addEventListener("click", () => {
            hintContent.classList.toggle("hidden");
            btnToggleHint.textContent =
                hintContent.classList.contains("hidden")
                    ? "Show hint"
                    : "Hide hint";
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
            codeResult.classList.toggle("ok", !!data.passed);
            codeResult.classList.toggle("bad", !data.passed);
        });
    }

    function escapeHtml(s) {
        return String(s).replace(/[&<>"']/g, m => ({
            "&": "&amp;",
            "<": "&lt;",
            ">": "&gt;",
            '"': "&quot;",
            "'": "&#039;"
        }[m]));
    }
});
