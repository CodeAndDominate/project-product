<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div>
    <form action="/login" method="post">
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
                <button type="submit">Войти</button>
            </div>
        </fieldset>
    </form>
</div>
<div>
    <form action="/registration" method="get">
        <fieldset>
            <div>
                <button type="submit">К регистрации</button>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
