<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cash</title>
</head>
<body>
<center>
    <br><br>
    您一共买了 ${sessionScope.ShoppingCart.bookNumber} 本书
    <br>
    应付: ￥ ${sessionScope.ShoppingCart.totalMoney}

    <br><br>

    <c:if test="${requestScope.errors != null}">
        <span style="color: red; ">${requestScope.errors}</span>
    </c:if>

    <form action="cash.do" method="post">

        <table cellpadding="10">
            <tr>
                <td>信用卡姓名:</td>
                <td><input type="text" name="username"/></td>
            </tr>
            <tr>
                <td>信用卡账号:</td>
                <td><input type="text" name="accountId"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Submit"/></td>
            </tr>
        </table>

    </form>

</center>

</body>
</html>
