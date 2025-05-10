<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<div>
    <form action="/registration" method="post">
        <fieldset>
            <div>
                <label id="login">
                    Логин: <input type="text" name="login">
                </label>
            </div>
            <div>
                <label id="password">
                    Пароль: <input type="password" name="password">
                </label>
            </div>
            <div>
                <button type="submit">Зарегистрироваться</button>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
