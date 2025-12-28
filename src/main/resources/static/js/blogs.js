// xử lý zoom in/out hình
document.addEventListener("DOMContentLoaded", () => {
    const img = document.getElementById("coverPreview");
    if (!img) return;

    img.addEventListener("load", () => {
        if (!img.src.includes("cover_placeholder")) {
            img.classList.remove("cover-default");
        }
    });

    img.addEventListener("error", () => {
        img.src = "/images/cover_placeholder.png";
        img.classList.add("cover-default");
    });
});