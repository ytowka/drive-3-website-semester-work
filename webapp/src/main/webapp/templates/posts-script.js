let currentPage = -1;
let loadingPage = null;
let loadedAll = false

const likeMap = new Map()

function loadPosts(url) {
    if(loadingPage !== currentPage){
        loadingPage = currentPage+1

        $.ajax({
            url: url,
            method: 'GET',
            data: {
                page: loadingPage,
                topic: topic,
            },
            success: function(data) {
                let object = JSON.parse(data)
                console.log("loaded page "+object.page)
                currentPage = object.page
                if (object.posts.length > 0) {
                    object.posts.forEach((post) => {
                        let postElement = document.createElement('div');
                        postElement.style.display = 'flex'
                        postElement.style.justifyContent = 'center'
                        postElement.style.padding = '0'
                        postElement.style.margin = '0'
                        postElement.style.width = '100%'

                        let likeIconClass = ""

                        likeMap.set(post.id, post.isLiked)
                        if(post.isLiked){
                            likeIconClass = "post-action-icon-clicked"
                        }else{
                            likeIconClass = "post-action-icon"
                        }

                        postElement.innerHTML = createPostCard(
                            post.id,
                            post.avatarUrl,
                            post.author,
                            post.authorUrl,
                            post.image,
                            post.text,
                            post.date,
                            post.likeCount,
                            likeIconClass,
                            post.topicUrl,
                            post.topicName
                        )
                        document.getElementById('posts-list').appendChild(postElement);
                    })
                } else {
                    loadedAll = true
                }
            },
            error: function() {

            },
            complete: function (){
                loadingPage = null
            }
        });
    }
}

function loadUserPosts(url, id) {
    if(loadingPage !== currentPage){
        loadingPage = currentPage+1

        $.ajax({
            url: url,
            method: 'GET',
            data: {
                userId: id
            },
            success: function(data) {
                let object = JSON.parse(data)
                console.log("loaded page "+object.page)
                currentPage = object.page
                if (object.posts.length > 0) {
                    object.posts.forEach((post) => {
                        let postElement = document.createElement('div');
                        postElement.style.width = '100%'
                        postElement.style.display = 'flex'
                        postElement.style.justifyContent = 'center'
                        postElement.style.padding = '0'
                        postElement.style.margin = '0'

                        let likeIconClass = ""

                        likeMap.set(post.id, post.isLiked)
                        if(post.isLiked){
                            likeIconClass = "post-action-icon-clicked"
                        }else{
                            likeIconClass = "post-action-icon"
                        }

                        postElement.innerHTML = createPostCard(
                            post.id,
                            post.avatarUrl,
                            post.author,
                            post.authorUrl,
                            post.image,
                            post.text,
                            post.date,
                            post.likeCount,
                            likeIconClass,
                            post.topicUrl,
                            post.topicName
                        )
                        document.getElementById('posts-list').appendChild(postElement);
                    })
                } else {
                    loadedAll = true
                }
            },
            error: function() {

            },
            complete: function (){
                loadingPage = null
            }
        });
    }

}

function onLike(id){
    if(userId.length > 0){
        console.log("like "+id)
        const icon = document.getElementById(`likeIcon${id}`)
        const count = document.getElementById(`likeCount${id}`)
        isLiked = likeMap.get(id);
        if(isLiked){
            icon.style.filter = "invert(1)"
            count.innerText = (parseInt(count.innerText) - 1).toString()
        }else{
            icon.style.filter = "invert(0.5) sepia(1) saturate(10) hue-rotate(320deg)"
            count.innerText = (parseInt(count.innerText) + 1).toString()
        }
        likeMap.set(id, !isLiked)
        $.ajax({
            url: `${contextPath}/api/like`,
            method: 'POST',
            contentType: "application/x-www-form-urlencoded",
            data: {
                postId: id,
                isLiked: !isLiked,
                userId: userId,
            },
        });
    }else{
        window.location.href=`http://localhost:8080${contextPath}/sign-in`
    }

}

function onComment(id){
    console.log("onComment "+id)
    window.location.href = "post/"+id
}

function createPostCard(
    id, avatar, author, authorUrl, image, text, date, likecount, iconClass, topicLink, topicName, likeblock
){
    return `
<div class="post-card">
    <div class="post-header">
       <img src="${avatar}" class="user-avatar" alt="">
       <div class="post-header-text">
           <a href="${authorUrl}">${author}</a>
           <p style="color: var(--subtext-color)">${date}</p>
        </div>
    </div>
    <a href="http://localhost:8080${contextPath}/post/${id}" target="_self">
         <img src="${image}" class="post-image" alt="">
    </a>
   <a class="post-text" href="http://localhost:8080${contextPath}/post/${id}" target="_self">
        <p style="margin: 16px">${text}</p> 
   </a>
  
   <div class="post-actions">
        <a href="http://localhost:8080${contextPath}/post/${id}" target="_self">
            <img class="post-action-icon" src="${contextPath}/res/img/ic_comment.svg" >
        </a>
       
        <div class="post-like-icon">
            <img class="${iconClass}" src="${contextPath}/res/img/ic_like.svg" onclick="onLike(\`${id}\`)" id="likeIcon${id}">
            <p style="color: white" id="likeCount${id}">${likecount}</p>
        </div>
        <div class="spacer"></div>
        <a class="topic-link" href="${topicLink}">${topicName}</a>
   </div>
 </div>
    `
}

