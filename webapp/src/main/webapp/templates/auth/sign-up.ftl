<#-- @ftlvariable username="contextPath" type="java.lang.String" -->
<#-- @ftlvariable username="usernameRegexPattern" type="java.lang.String" -->

<meta charset="UTF-8"><link href="${contextPath}/templates/auth/styles.css" rel="stylesheet" type="text/css" />
<#import "/macros/base-page.ftl" as ui/>
<@ui.page selectedTab = "" title="sign up" userAvatar=userAvatar!"">
    <script >
        const contextPath = "${contextPath}"
        const usernameRegexPattern = "${usernameRegexPattern}"
        const emailRegexPattern = "${emailRegexPattern}"
        const minNameLength = ${minNameLength}
        const maxNameLength = ${maxNameLength}
        const minPasswordLength = ${minPasswordLength}
    </script>
    <div class="main-content">
        <div class="card">
            <form id="registration-form">
                <div style=" grid-column-start: 1; grid-column-end: 3; display: flex; flex-direction: column; justify-content: center; align-items: center">
                    <div class="profile-container">
                        <img src="${contextPath}/templates/auth/photo.svg" alt="Profile Picture" id="profile-picture" onclick="openFileSelector()">
                        <input type="file" name="picture" id="profile-image-input" accept="image/*" onchange="previewImage(event)">
                        <img src="${contextPath}/templates/auth/cancel.svg" id="clear-button" onclick="clearImage()"  alt="sf">
                    </div>
                    <p id="file-error-label" class="error-message">Файл слишком большой</p>
                </div>
                <div class="field-container">
                    <label>
                        Имя пользователя
                        <input name="username">
                    </label>
                    <p id="username-error-label" class="error-message">имя пользователя уже занято</p>
                </div>

                <div class="left-column">
                    <label >
                        Имя
                        <input name="firstname">
                    </label>
                    <p id="name-error-label" class="error-message">имя должно быть от ${minNameLength} до ${maxNameLength} символов</p>
                </div>


                <div class="right-column">
                    <label>
                        Фамилия
                        <input name="surname">
                    </label>
                    <p id="surname-error-label" class="error-message">Фамилия должна быть от ${minNameLength} до ${maxNameLength} символов</p>
                </div>
                <div class="field-container">
                    <label>
                        Эл. почта
                        <input name="email">
                    </label>
                    <p id="email-error-label" class="error-message">пользователь с такой электронной почтой уже зарегестрирован</p>
                </div>

                <div style=" grid-column-start: 1; grid-column-end: 3; display: flex; flex-direction: column; align-items: center; justify-content: center; gap: 20px">
                    <div class="field-container">
                        <label>
                            Пароль
                            <input name="password" type="password">
                        </label>
                        <p id="password-too-short-label" class="error-message">Пароль должен быть минимум ${minPasswordLength} символов</p>
                    </div>
                    <div class="field-container">
                        <label>
                            Повтор пароля
                            <input name="passwordRepeat" type="password">
                        </label>
                        <p id="password-not-match-label" class="error-message">Пароли не совпадают</p>
                    </div>
                    <label style="display: flex; flex-direction: row; width: 100%; justify-content: center; align-items: center">
                        <input type="checkbox" name="rememberMe" style="width: 20px; height: 20px;">
                        Запомнить меня
                    </label>
                    <button type="submit" name="submit">Создать аккаунт</button>
                    <a href="sign-in">Войти в аккаунт</a>
                </div>
            </form>
        </div>
        <script src="${contextPath}/templates/auth/scripts.js" ></script>
    </div>
</@ui.page>
