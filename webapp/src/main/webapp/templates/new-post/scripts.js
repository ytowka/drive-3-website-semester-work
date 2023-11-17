let uploadPicture = false;
function openFileSelector() {
    const input = document.getElementById('image-input');
    input.click();
}

const form = document.getElementById('new-post-form');

function previewImage(event) {
    const input = event.target;
    const reader = new FileReader();

    reader.onload = function() {
        const img = document.getElementById('picture');
        img.src = reader.result;
        uploadPicture = true;
    };
    reader.readAsDataURL(input.files[0]);
}

function processForm(e) {
    if (e.preventDefault) e.preventDefault();
    const formData = new FormData(form)

    const text = form.text.value

    const isEmpty = text.length === 0

    setVisible(document.getElementById("text-error"), isEmpty)

    if(!isEmpty){
        if(!uploadPicture){
            formData.delete("picture")
        }

        $.ajax({
            url: `http://localhost:8080${contextPath}/new-post`,
            data: formData,
            processData: false,
            contentType: false,
            method: 'POST',
            success: function (data) {
                let response = JSON.parse(data)
                console.log(data)
                window.location.href = `http://localhost:8080${contextPath}/post/${response.id}`;
            },
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
