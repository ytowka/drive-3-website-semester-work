function openFileSelector() {
    const input = document.getElementById('profile-image-input');
    input.click();
}
function previewImage(event) {
    const input = event.target;
    const reader = new FileReader();
    const clear = document.getElementById('clear-button')

    reader.onload = function() {
        const img = document.getElementById('profile-picture');
        clear.style.display = "block"
        img.src = reader.result;
        img.classList.add('preview');
    };

    reader.readAsDataURL(input.files[0]);
}

function clearImage() {
    const img = document.getElementById('profile-picture');
    const input = document.getElementById('profile-image-input');
    const clear = document.getElementById('clear-button')

    clear.style.display = "none"
    img.src = 'photo.svg';
    img.classList.remove('preview');
    input.value = '';
}

function processForm(e) {
    if (e.preventDefault) e.preventDefault();


    /* do what you want with the form */

    console.log("submit form")

    // You must return false to prevent the default form behavior
    return false;
}

const form = document.getElementById('registration-form');
form.onsubmit = processForm