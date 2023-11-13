<#import "/macros/base-page.ftl" as ui/>
<meta charset="UTF-8"><link href="${contextPath}/templates/feed/styles.css" rel="stylesheet" type="text/css" />
<@ui.page selectedTab = "" title="feed" userAvatar=userAvatar!"">
    <div class="container">
        <div class="content">
            <#list posts as post>
                <div class="post-card">
                    ${post.content()}
                </div>
            </#list>
        </div>
    </div>
</@ui.page>