<#import "/macros/base-page.ftl" as ui/>
<meta charset="UTF-8"><link href="${contextPath}/templates/profile/styles.css" rel="stylesheet" type="text/css" />
<@ui.page selectedTab = "" title="sign up" userAvatar=userAvatar!"">
    <script>const contextPath = "${contextPath}"</script>

    <div class="container">
        <div class="content" id="posts-list">
            <div class="profile-card">
                <div id="profile" class="profile-container">
                    <div class="main-info">
                        <img class="profile-image" src="avatar.png" alt="Аватар пользователя">
                        <div class="username">
                            <h1>Имя Пользователя</h1>
                            <button id="subscribeButton" onclick="onSubscribeClick()">Подписаться</button>
                        </div>
                    </div>
                    <p>Иван Иванов</p>
                    <p>Дата регистрации: 01.01.2022</p>
                </div>
            </div>
        </div>
    </div>

    <script src="${contextPath}/templates/posts-script.js"></script>
    <script src="${contextPath}/templates/profile/scripts.js"></script>
    <script>loadPosts("feed-api")</script>
</@ui.page>