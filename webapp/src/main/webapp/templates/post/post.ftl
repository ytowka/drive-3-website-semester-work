<meta charset="UTF-8"><link href="${contextPath}/templates/default-styles.css" rel="stylesheet" type="text/css"/>
<meta charset="UTF-8"><link href="${contextPath}/templates/post/styles.css" rel="stylesheet" type="text/css" />
<script>
    const contextPath = "${contextPath}"
    const postId = "${postId}"
    let isLiked = "${isLiked?string}"
</script>
<#import "/macros/base-page.ftl" as ui/>
<@ui.page selectedTab = "" title="sign up" userAvatar=userAvatar!"">
    <div class="content">
        <div class="post-container">
            <div class="post-card">
                <div class="post-header">
                    <img src="${avatar}" class="user-avatar" alt="">
                    <div class="post-header-text">
                        <a href="${authorUrl}">${author}</a>
                        <p style="color: var(--subtext-color)">${date}</p>
                    </div>
                </div>
                <img src="${image}" class="post-image" alt="">
                <p class="post-text">${text}</p>

                <div class="post-actions">
                    <div class="post-like-icon">
                        <img class="<#if isLiked>post-action-icon<#else>post-action-icon-clicked</#if>" src="${contextPath}/res/img/ic_like.svg" onclick="onLike()" id="likeIcon">
                        <p style="color: white" id="likeCount">${likeCount}</p>
                    </div>
                    <div class="spacer"></div>
                    <a class="topic-link" href="${topicLink}">${topicName}</a>
                </div>
            </div>
            <div class="post-card">
                <form method="post" class="comment-block" id="comment-form" >
                    <label>
                        Оставить комментарий
                        <textarea name="text"></textarea>
                    </label>
                    <p class="error-message" id="text-error">Поле пустое</p>
                    <button type="submit">Отпарвить</button>
                </form>
            </div>
            <div class="post-card">
                <div class="comment-list">
                    <#list comments as comment>
                        <div class="comment">
                            <div class="post-header">
                                <img src="${comment.avatarUrl()}" class="user-avatar" alt="">
                                <div class="post-header-text">
                                    <a href="${comment.authorUrl()}">${comment.name()}</a>
                                    <p style="color: var(--subtext-color)">${comment.date()}</p>
                                </div>
                            </div>
                            <p class="comment-text">${comment.text()}</p>
                            <hr class="solid">
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
    <script src="${contextPath}/templates/post/script.js"></script>
</@ui.page>