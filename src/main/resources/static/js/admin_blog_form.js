const coverClickArea = document.getElementById("coverClickArea");
const coverInput = document.getElementById("coverInput");
const coverPreview = document.getElementById("coverPreview");
const coverPath = document.getElementById("coverPath");
const coverError = document.getElementById("coverError");

coverClickArea.addEventListener("click", () => coverInput.click());

coverInput.addEventListener("change", () => {
    const file = coverInput.files[0];
    if (!file) return;

    // preview
    coverPreview.src = URL.createObjectURL(file);

    const formData = new FormData();
    formData.append("file", file);

    fetch("/admin/blogs/upload-cover", {
        method: "POST",
        body: formData
    })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                coverPath.value = data.path;
                clearError("cover");
            } else {
                showError("cover", "Upload failed");
            }
        })
        .catch(() => showError("cover", "Server error"));
});

// xử lý zoom in/out hình
document.addEventListener("DOMContentLoaded", () => {
    const img = document.getElementById("coverPreview");
    if (!img) return;

    img.addEventListener("load", () => {
        if (!img.src.includes("blog_cover_placeholder")) {
            img.classList.remove("cover-default");
        }
    });

    img.addEventListener("error", () => {
        img.src = "/images/blog_cover_placeholder.png";
        img.classList.add("cover-default");
    });
});