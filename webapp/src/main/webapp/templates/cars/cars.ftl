<meta charset="UTF-8"><link href="${contextPath}/templates/cars/styles.css" rel="stylesheet" type="text/css" />
<#import "/macros/base-page.ftl" as ui/>
<@ui.page selectedTab = "" title="sign up" userAvatar=userAvatar!"">
    <div class="content">
        <div class="cars-grid">
            <#list cars as car>
                <div class="car-card">
                        <div class="image-container">
                            <a href="${car.link()}">
                                <img class="car-image" src="${car.picture()}">
                            </a>
                        </div>
                    <a class="car-name" href="${car.link()}">${car.name()}</a>
                </div>
            </#list>
        </div>
    </div>
</@ui.page>