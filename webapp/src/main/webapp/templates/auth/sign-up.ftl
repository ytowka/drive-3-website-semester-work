<#-- @ftlvariable username="contextPath" type="java.lang.String" -->
<#-- @ftlvariable username="usernameRegexPattern" type="java.lang.String" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"><link href="${contextPath}/templates/auth/styles.css" rel="stylesheet" type="text/css" />
    <meta charset="UTF-8"><link href="${contextPath}/templates/default-styles.css" rel="stylesheet" type="text/css" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;700;900&display=swap" rel="stylesheet">
    <script src="${contextPath}/res/jquery-3.7.1.min.js"></script>
    <title>Sign up</title>
</head>
<body>
<!-- Section: Design Block -->
<script >
    const contextPath = "${contextPath}"
    const usernameRegexPattern = "${usernameRegexPattern}"
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
                    <input name="email" type="email">
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
<!-- Section: Design Block -->
</body>
</html>