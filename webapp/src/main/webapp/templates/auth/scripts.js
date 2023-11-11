function openFileSelector() {
    const input = document.getElementById('profile-image-input');
    input.click();
}

let uploadPicture = false;
const usernameRe = new RegExp(usernameRegexPattern);
const maxFileSize = 1024*1024

function previewImage(event) {
    const input = event.target;
    const reader = new FileReader();
    const clear = document.getElementById('clear-button')

    const fileErrorLabel = document.getElementById("file-error-label")

    reader.onload = function() {
        console.log(reader.result.length)
        if(reader.result.length > maxFileSize){
            setVisible(fileErrorLabel, true)
        }else{
            setVisible(fileErrorLabel, false)
            const img = document.getElementById('profile-picture');
            clear.style.display = "block"
            img.src = reader.result;
            img.classList.add('preview');
        }
    };
    uploadPicture = true
    reader.readAsDataURL(input.files[0]);
}

function clearImage() {
    const img = document.getElementById('profile-picture');
    const input = document.getElementById('profile-image-input');
    const clear = document.getElementById('clear-button')

    uploadPicture = false
    clear.style.display = "none"
    img.src = 'templates/auth/photo.svg';
    img.classList.remove('preview');
    input.value = '';
}

const form = document.getElementById('registration-form');
function processForm(e) {
    if (e.preventDefault) e.preventDefault();

    const formData = new FormData(form)

    console.log(formData)

    const password = form.password.value
    const passwordRepeat = form.passwordRepeat.value
    const username = form.username.value
    const name = form.firstname.value
    const surname = form.surname.value

    const emailErrorLabel  =document.getElementById("email-error-label")
    const passwordRepeatErrorLabel = document.getElementById("password-not-match-label")
    const passwordErrorLabel = document.getElementById("password-too-short-label")
    const usernameErrorLabel = document.getElementById("username-error-label")
    const nameErrorLabel = document.getElementById("name-error-label")
    const surnameErrorLabel = document.getElementById("surname-error-label")

    const isPasswordToShort = password.length < minPasswordLength
    const isPasswordRepeatWrong = password !== passwordRepeat
    const isUserNameLengthOutOfRange = username.length < minNameLength || username.length > maxNameLength
    const isNameLengthOutOfRange = name.length < minNameLength || username.length > maxNameLength
    const isSurnameLengthOutOfRange = surname.length < minNameLength || username.length > maxNameLength

    const isUserNameNotValid = !usernameRe.test(username)

    setVisible(passwordRepeatErrorLabel, isPasswordRepeatWrong)
    setVisible(passwordErrorLabel, isPasswordToShort)
    setVisible(usernameErrorLabel, isUserNameLengthOutOfRange || isUserNameNotValid)
    setVisible(nameErrorLabel, isNameLengthOutOfRange)
    setVisible(surnameErrorLabel, isSurnameLengthOutOfRange)
    setVisible(emailErrorLabel, false)

    if(isUserNameLengthOutOfRange){
        usernameErrorLabel.innerText = `имя пользователя должно быть от ${minNameLength} до ${maxNameLength} символов`
    }else if(isUserNameNotValid){
        usernameErrorLabel.innerText = "имя пользователя не может содержать пробелы, только символы английского алфавиты, цифры или _ -"
    }

    const haveErrors = isPasswordToShort || isPasswordRepeatWrong || isUserNameLengthOutOfRange || isNameLengthOutOfRange || isSurnameLengthOutOfRange || isUserNameNotValid


    if(!haveErrors){
        if(!uploadPicture){
            formData.delete("picture")
        }
        formData.delete("passwordRepeat")

        $.ajax({
            url: `http://localhost:8080${contextPath}/sign-up`,
            data: formData,
            processData: false,
            contentType: false,
            method: 'POST',
            success: function (data) {
                console.log(data)
                window.location.href = "feed";
            },
        }).fail(function (xhr){
            const data = xhr.responseJSON;

            if(data.message === "username-already-used"){
                usernameErrorLabel.innerText = "имя пользователя уже занято"
                setVisible(usernameErrorLabel, true)
            } else if(data.message === "email-already-used"){
                setVisible(emailErrorLabel, true)
            } else{

            }
            console.log(data);
        })
    }
    return false;
}

function setVisible(e, visible){
    if(visible){
        e.style.display = "block"
    }else{
        e.style.display = "none"
    }
}

form.onsubmit = processForm