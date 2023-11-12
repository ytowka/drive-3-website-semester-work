<#macro page selectedTab title="title">
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8"><link href="${contextPath}/templates/default-styles.css" rel="stylesheet" type="text/css" />
        <meta charset="UTF-8"><link href="${contextPath}/templates/macros/styles.css" rel="stylesheet" type="text/css" />
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;700;900&display=swap" rel="stylesheet">
        <script src="${contextPath}/res/jquery-3.7.1.min.js"></script>
        <#import "/macros/page-blocks.ftl" as ui/>
        <meta charset="UTF-8">
        <title>${title}</title>
    </head>
    <body>
    <@ui.header selectedTab = ""/>
    <div style="padding-top: 60px">
        <#nested>
    </div>
    <@ui.footer />
    </body>
    </html>
</#macro>