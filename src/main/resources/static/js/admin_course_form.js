document.addEventListener("DOMContentLoaded", () => {

    const coverClickArea = document.getElementById("coverClickArea");
    const coverInput = document.getElementById("coverInput");
    const coverPreview = document.getElementById("coverPreview");
    const coverPath = document.getElementById("coverPath");

    if (!coverClickArea || !coverInput) return;

    coverClickArea.addEventListener("click", () => coverInput.click());

    coverInput.addEventListener("change", () => {
        const file = coverInput.files[0];
        if (!file) return;

        // preview
        coverPreview.src = URL.createObjectURL(file);
        coverPreview.classList.remove("cover-default");

        const formData = new FormData();
        formData.append("file", file);

        fetch("/admin/courses/upload-cover", {
            method: "POST",
            body: formData
        })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    coverPath.value = data.path;
                } else {
                    alert("Upload failed");
                }
            })
            .catch(() => alert("Server error"));
    });

    coverPreview.addEventListener("error", () => {
        coverPreview.src = "/images/course_cover_placeholder.png";
        coverPreview.classList.add("cover-default");
    });

    coverPreview.addEventListener("load", () => {
        if (!coverPreview.src.includes("course_cover_placeholder")) {
            coverPreview.classList.remove("cover-default");
        }
    });
});

const priceInput = document.getElementById("priceInput");
const priceValue = document.getElementById("priceValue");

priceInput.addEventListener("input", () => {
    let raw = priceInput.value.replace(/[^\d]/g, "");
    if (raw === "") {
        priceValue.value = "";
        return;
    }
    priceValue.value = raw;
    priceInput.value = Number(raw).toLocaleString("en-US");
});
