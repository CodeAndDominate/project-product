<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<div>
    <form action="/products" method="post">
        <fieldset>
            <div>
                <label id="name">
                    Имя продукта: <input type="text" name="name">
                </label>
            </div>
            <div>
                <label id="imageUrl">
                    Ссылка на изображение: <input type="text" name="imageUrl">
                </label>
            </div>
            <div>
                <button type="submit">Создать продукт</button>
            </div>
        </fieldset>
    </form>
    <div>
        <div>
            <label>
                Сейчас на сайте:
            </label>
        </div>
        <div>
            <c:forEach items="${applicationScope.USER_LIST}" var="user">
                ${user}<br>
            </c:forEach>
        </div>
    </div>
    <div>
        <form action="/logout" method="get">
            <button type="submit">Выйти из аккаунта</button>
        </form>
    </div>
</div>
<c:forEach items="${sessionScope.USER_PRODUCTS}" var="product">
    <form action="/products" method="post">
        <input type="text" contenteditable="false" name="productId" hidden="hidden" value="${product.id}">
        <div>
                ${product.name}
        </div>
        <span>
            <button type="submit">Удалить продукт</button>
        </span>
        <img src="${product.imageUrl}">
    </form>
</c:forEach>
</body>
</html>
