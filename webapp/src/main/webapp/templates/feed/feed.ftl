<#import "/macros/base-page.ftl" as ui/>
<meta charset="UTF-8"><link href="${contextPath}/templates/feed/styles.css" rel="stylesheet" type="text/css" />
<@ui.page selectedTab = "" title="feed" userAvatar=userAvatar!"">
    <script>
        const contextPath = "${contextPath}"
        const feedApiPath = "${feedApiPath}"
    </script>
    <div class="container">
        <#if topic?has_content>
            <h1>Тема: ${topic}</h1>
        </#if>
        <div class="content" id="posts-list">

        </div>
    </div>
    <script src="${contextPath}/templates/posts-script.js"></script>
    <script src="${contextPath}/templates/feed/scripts.js"></script>
</@ui.page>