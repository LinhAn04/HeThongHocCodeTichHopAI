const slides = document.querySelectorAll(".banner-slider");

let index = 0;

function showSlide(i) {
    slides.forEach(s => {
        s.classList.remove("active");
        s.style.zIndex = 0;
    });

    slides[i].classList.add("active");
    slides[i].style.zIndex = 2;
}

// init
showSlide(index);

// Auto-play
setInterval(() => {
    index = (index + 1) % slides.length;
    showSlide(index);
}, 4000);
