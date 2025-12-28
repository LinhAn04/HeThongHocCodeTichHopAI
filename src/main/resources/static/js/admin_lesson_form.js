document.addEventListener("DOMContentLoaded", () => {
    // change tab base on type lesson dropdown
    const typeSelect = document.getElementById("lessonType");
    const fragments = document.querySelectorAll(".lesson-fragment");

    function updateFragments() {
        const selectedType = typeSelect.value;

        fragments.forEach(f => {
            const isActive = f.dataset.type === selectedType;

            f.style.display = isActive ? "block" : "none";

            f.querySelectorAll("input, textarea, select").forEach(el => {
                if (!isActive) {
                    el.dataset.required = el.required; // backup
                    el.required = false;
                } else {
                    if (el.dataset.required === "true") {
                        el.required = true;
                    }
                }
            });
        });
    }

    if (typeSelect) {
        updateFragments();
        typeSelect.addEventListener("change", updateFragments);
    }
});

// quiz page
function addQuestion() {
    const container = document.getElementById("quiz-container");
    const qIndex = container.children.length;

    const html = `
    <div class="quiz-question">

        <div class="quiz-header">
            <span>Question ${qIndex + 1}</span>
            <button type="button"
                    class="btn-close"
                    onclick="removeQuestion(this)">✕</button>
        </div>

        <label>Question content</label>
        <textarea name="quizForm.quizCauHoi[${qIndex}].noiDung"
                  placeholder="Enter question..."
                  required></textarea>

        ${[0,1,2,3].map(i => `
            <div class="quiz-option"
                 onclick="selectOption(${qIndex}, ${i})">

                <div class="option-row">
                    <span class="option-label">${"ABCD"[i]}</span>

                    <input type="text"
                           name="quizForm.quizCauHoi[${qIndex}].luaChon[${i}]"
                           placeholder="Answer option..."
                           required>
                </div>

                <textarea class="explain-input"
                          name="quizForm.quizCauHoi[${qIndex}].giaiThichSai[${i}]"
                          placeholder="Explanation (used if False)"></textarea>
            </div>
        `).join("")}

        <label>Correct explanation</label>
        <textarea name="quizForm.quizCauHoi[${qIndex}].giaiThichDung"
                  placeholder="Explanation for the TRUE answer"></textarea>

        <input type="hidden"
               name="quizForm.quizCauHoi[${qIndex}].dapAnDungIndex">
    </div>
    `;

    container.insertAdjacentHTML("beforeend", html);
}


function removeQuestion(btn) {
    btn.closest(".quiz-question").remove();
    reindexQuestions();
}

function reindexQuestions() {
    const questions = document.querySelectorAll(".quiz-question");

    questions.forEach((q, index) => {

        q.querySelectorAll("input, textarea").forEach(el => {

            if (!el.name) return;

            el.name = el.name.replace(
                /quizCauHoi\[\d+]/,
                `quizCauHoi[${index}]`
            );
        });
    });
}

function selectOption(qIndex, optionIndex) {

    const question = document.querySelectorAll(".quiz-question")[qIndex];
    const options = question.querySelectorAll(".quiz-option");

    options.forEach((opt, i) => {
        if (i === optionIndex) {
            opt.classList.add("selected");
        } else {
            opt.classList.remove("selected");
        }
    });

    const hidden = question.querySelector(
        `input[name="quizForm.quizCauHoi[${qIndex}].dapAnDungIndex"]`
    );
    if (hidden) hidden.value = optionIndex;
}
