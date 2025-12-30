document.addEventListener("DOMContentLoaded", () => {

    const coverClickArea = document.getElementById("coverClickArea");
    const coverInput = document.getElementById("coverInput");
    const coverPreview = document.getElementById("coverPreview");
    const coverPath = document.getElementById("coverPath");

    if (coverClickArea && coverInput) {
        coverClickArea.addEventListener("click", () => coverInput.click());

        coverInput.addEventListener("change", () => {
            const file = coverInput.files[0];
            if (!file) return;

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
    }

    const priceInput = document.getElementById("priceInput");
    const priceValue = document.getElementById("priceValue");
    const priceError = document.getElementById("priceError");
    const form = document.querySelector("form");

    if (priceInput && priceValue) {

        if (!priceValue.value) {
            priceInput.value = "";
        } else {
            priceInput.value = Number(priceValue.value).toLocaleString("en-US");
        }

        priceInput.addEventListener("input", () => {
            let raw = priceInput.value.replace(/\D/g, "");

            if (raw === "") {
                priceValue.value = "";
                priceError.style.display = "none";
                return;
            }

            priceValue.value = raw;
            priceInput.value = Number(raw).toLocaleString("en-US");

            if (parseInt(raw) < 1000) {
                priceError.style.display = "block";
            } else {
                priceError.style.display = "none";
            }
        });

        form.addEventListener("submit", (e) => {
            const price = parseInt(priceValue.value || "0");

            if (price < 1000) {
                e.preventDefault();
                priceError.style.display = "block";
                priceInput.focus();
            }
        });
    }
});
