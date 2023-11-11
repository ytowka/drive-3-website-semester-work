const authForm = document.getElementById('login-form');

function processLoginForm(e) {
    if (e.preventDefault) e.preventDefault();

    const authFormData = new FormData(authForm)

    console.log(Array.from(authFormData))

    const errorLabel = document.getElementById("error-label");

    setVisible(errorLabel, false)


    $.ajax({
        url: `http://localhost:8080${contextPath}/sign-in`,
        data: authFormData,
        contentType: false,
        processData: false,
        type: 'POST',
        success: function (data) {
            console.log(data)
            window.location.href = "feed";
        },
    }).fail(function (xhr){
        const data = xhr.responseJSON;
        setVisible(errorLabel, true)
        console.log(data);
    })
    return false;
}

function setVisible(e, visible){
    if(visible){
        e.style.display = "block"
    }else{
        e.style.display = "none"
    }
}

authForm.onsubmit = processLoginForm