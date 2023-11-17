function onLike(){
    console.log("like "+postId)
    const icon = document.getElementById(`likeIcon`)
    const count = document.getElementById(`likeCount`)
    isLiked = !isLiked
    if(isLiked){
        icon.style.filter = "invert(1)"
        count.innerText = (parseInt(count.innerText) - 1).toString()
    }else{
        icon.style.filter = "invert(0.5) sepia(1) saturate(10) hue-rotate(320deg)"
        count.innerText = (parseInt(count.innerText) + 1).toString()
    }

    $.ajax({
        url: `http://localhost:8080/${contextPath}/api/like`,
        method: 'POST',
        contentType: "application/x-www-form-urlencoded",
        data: {
            postId: postId,
            isLiked: !isLiked,
            userId: userId,
        },
    });
}

const form = document.getElementById('comment-form');

function processForm(e) {
    e.preventDefault();
    const formData = new FormData(form)

    const text = form.text.value

    const isEmpty = text.length === 0

    if(!isEmpty){

        $.ajax({
            url: `http://localhost:8080${contextPath}/api/comment`,
            data: {
                "text": text,
                "postId": postId,
            },
            contentType: "application/x-www-form-urlencoded",
            //contentType: false,
            method: 'POST',
            success: function (data) {
                console.log(data)
                window.location.reload();
            },
        })
    }

    setVisible(document.getElementById("text-error"), isEmpty)

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