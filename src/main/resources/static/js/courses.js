const slides = document.querySelectorAll(".banner-slider");
const dots = document.querySelectorAll(".dot");

let index = 0;

function showSlide(i) {
    slides.forEach(s => s.classList.remove("active"));
    dots.forEach(d => d.classList.remove("active"));

    slides[i].classList.add("active");
    dots[i].classList.add("active");
}

// Auto-play
setInterval(() => {
    index = (index + 1) % slides.length;
    showSlide(index);
}, 4000);

// Click on dots
dots.forEach(d =>
    d.addEventListener("click", () => {
        index = parseInt(d.dataset.index);
        showSlide(index);
    })
);