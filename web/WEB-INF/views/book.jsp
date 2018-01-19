<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/common.jsp" %>
<%@ include file="/commons/queryCondition.jsp" %>
<html>
<head>
    <title>Book</title>
</head>
<body>
<center>

    <br><br>
    Title: ${book.title}
    <br><br>
    Author: ${book.author}
    <br><br>
    Price: ${book.price}
    <br><br>
    PublishingDate: ${book.publishingDate}
    <br><br>
    Remark: ${book.remark}
    <br><br>

    <a href="getBooks.do?pageNo=${param.pageNo}">继续购物</a>

</center>
</body>
</html>
