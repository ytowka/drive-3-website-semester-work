<#macro header selectedTab userAvatar="">
    <div class="header">
        <div class="header-content">
            <p class="header-label-selected">DRIVE 3</p>
            <a class="header-label" href="http://localhost:8080${contextPath}/feed">Лента</a>
            <a class="header-label" href="http://localhost:8080${contextPath}/users">Пользователи</a>
            <a class="header-label" href="http://localhost:8080${contextPath}/cars">Автомобили</a>
            <#if userAvatar?has_content>
                <a class="header-label" href="http://localhost:8080${contextPath}/new-post">написать пост</a>
            </#if>
            <div class="spacer"></div>
            <#if userAvatar?has_content>
                <a href="profile">
                    <img class="user-avatar" src="${userAvatar}"/>
                </a>
            <#else>
                <div class="auth-buttons-container">
                    <button onclick="window.location.href = 'sign-up'" class="login-button">Создать аккаунт</button>
                    <button onclick="window.location.href = 'sign-in'" class="reg-button" >Войти</button>
                </div>
            </#if>
        </div>
    </div>
</#macro>