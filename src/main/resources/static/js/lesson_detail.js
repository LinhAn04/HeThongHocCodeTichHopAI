const btn = document.getElementById("btnGrade");
if (btn) {
    btn.addEventListener("click", async () => {
        const code = document.getElementById("codeInput").value;
        const out = document.getElementById("gradeResult");
        out.innerHTML = `<div class="muted">Đang chấm...</div>`;

        // TODO: nối backend AI sau
        // const res = await fetch("/api/ai/grade", { method:"POST", headers:{...}, body: JSON.stringify({...}) });
        // const data = await res.json();

        // Placeholder
        setTimeout(() => {
            out.innerHTML = `
        <div><b>Score:</b> 70/100</div>
        <div class="muted">Gợi ý: xử lý thêm điều kiện biên, format output.</div>
        <div class="muted">Highlight lỗi: (mình sẽ làm khi bạn gửi yêu cầu cụ thể backend AI + format response)</div>
      `;
        }, 600);
    });
}
