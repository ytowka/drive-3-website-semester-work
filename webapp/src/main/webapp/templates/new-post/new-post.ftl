<#import "/macros/base-page.ftl" as ui/>
<meta charset="UTF-8"><link href="${contextPath}/templates/default-styles.css" rel="stylesheet" type="text/css"/>
<script>const contextPath = "${contextPath}"</script>
<@ui.page selectedTab = "" title="Новый пост" userAvatar=userAvatar!"">
    <meta charset="UTF-8"><link href="${contextPath}/templates/new-post/styles.css" rel="stylesheet" type="text/css" />
    <div class="content">

        <h1>Новый пост</h1>
        <form method="post" class="post-container" id="new-post-form">
            <div class="post-card">
                <img src="${contextPath}/res/img/car.svg" class="car-image" id="picture" onclick="openFileSelector()">
                <input name="image" type="file" style="display: none" id="image-input" onchange="previewImage(event)">
                <div class="post-content">
                    <label>
                        Текст
                        <textarea name="text"></textarea>
                    </label>
                    <p class="error-message" id="text-error">Текст не может быть пустым</p>
                    <label>
                        Тема
                        <select name="topic">
                            <#list topics as topic>
                                <option value="${topic.id().toString()}">
                                    ${topic.name()}
                                </option>
                            </#list>
                        </select>
                    </label>
                </div>
            </div>
            <button type="submit">Опубликовать</button>
        </form>
    </div>
    <style>base{
            color:white;
        }</style>
    <script src="${contextPath}/templates/new-post/scripts.js"></script>
</@ui.page>