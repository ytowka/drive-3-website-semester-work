<#macro page selectedTab userAvatar="" title="title">
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8"><link href="${contextPath}/templates/default-styles.css" rel="stylesheet" type="text/css" />
        <meta charset="UTF-8"><link href="${contextPath}/templates/macros/styles.css" rel="stylesheet" type="text/css" />
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;700;900&display=swap" rel="stylesheet">
        <script src="${contextPath}/res/jquery-3.7.1.min.js"></script>
        <#import "/macros/header.ftl" as ui/>
        <meta charset="UTF-8">
        <title>${title}</title>
    </head>
    <body>
    <@ui.header selectedTab = "" userAvatar = userAvatar/>
    <div style="padding-top: 60px; display: flex; justify-content: center">
        <#nested>
    </div>
    <#include "/footer.ftl">
    </body>
    </html>
</#macro>