<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"><link href="${contextPath}/templates/auth/styles.css" rel="stylesheet" type="text/css" />
    <meta charset="UTF-8"><link href="${contextPath}/templates/default-styles.css" rel="stylesheet" type="text/css" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;700;900&display=swap" rel="stylesheet">
    <script src="${contextPath}/res/jquery-3.7.1.min.js"></script>
    <title>Sign in</title>
</head>
<body>
<script >
    const contextPath = "${contextPath}"
</script>
<div class="main-content">
    <div class="card">
        <form id="login-form" method="POST">
            <div class="field-container">
                <label>
                    Имя пользователя или email
                    <input name="login">
                </label>
            </div>
            <div style=" grid-column-start: 1; grid-column-end: 3; display: flex; flex-direction: column; align-items: center; justify-content: center; gap: 20px">
                <div class="field-container">
                    <label>
                        Пароль
                        <input name="password" type="password">
                    </label>
                </div>
                <label style="display: flex; flex-direction: row; width: 100%; justify-content: center; align-items: center">
                    <input type="checkbox" name="rememberMe" style="width: 20px; height: 20px;">
                    Запомнить меня
                </label>
                <p id="error-label" class="error-message">неверное имя пользователя или пароль</p>
                <button name="submit" type="submit">Войти</button>
                <span>
                    <a href="sign-up">Создать аккаунт</a>
                </span>

            </div>
        </form>
    </div>
    <script src="${contextPath}/templates/auth/auth-scripts.js"></script>

</div>
</body>
</html>