<#import "/macros/base-page.ftl" as ui/>
<meta charset="UTF-8"><link href="${contextPath}/templates/users/styles.css" rel="stylesheet" type="text/css" />
<@ui.page selectedTab = "" title="Подписки" userAvatar=userAvatar!"">
    <script>
        const contextPath = "${contextPath}"
        const apiPath = "${apiPath}"
    </script>
    <div class="content">
        <h1 class="title">Подписки</h1>
        <div id="user-list" class="user-list">

        </div>
    </div>
    <script src="${contextPath}/templates/users/user-search-scripts.js"></script>
</@ui.page>