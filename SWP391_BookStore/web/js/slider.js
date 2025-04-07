var imgFeature = document.querySelector('.img-feature');
var listImg = document.querySelectorAll('.list-image img');
var prevBtn = document.querySelector(".prev");
var nextBtn = document.querySelector(".next");

var currentIndex = 0;

function updateImageByIndex(index) {
    // Remove active class
    document.querySelectorAll('.list-image div').forEach(item => {
        item.classList.remove('active');
    });

    currentIndex = index;
    imgFeature.src = listImg[index].getAttribute('src');
    imgFeature.setAttribute('data-link', listImg[index].getAttribute('data-link'));
    listImg[index].parentElement.classList.add('active');
}

// Add click event to main image
imgFeature.addEventListener('click', function() {
    const link = this.getAttribute('data-link');
    if (link) {
        window.location.href = link;
    }
});

listImg.forEach((imgElement, index) => {
    imgElement.addEventListener('click', e=>{
        updateImageByIndex(index);
    });
});

prevBtn.addEventListener('click', e => {
    if (currentIndex === 0) {
        currentIndex = listImg.length - 1;
    } else {
        currentIndex--;
    }
    updateImageByIndex(currentIndex);
});

nextBtn.addEventListener('click', e => {
    if (currentIndex === listImg.length - 1) {
        currentIndex = 0;
    } else {
        currentIndex++;
    }
    updateImageByIndex(currentIndex);
});

updateImageByIndex(0);
