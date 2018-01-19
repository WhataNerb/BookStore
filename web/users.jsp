<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/common.jsp" %>
<html>
<head>
    <title>Users</title>
</head>
<body>

<center>
    <br><br>
    <form action="userServlet" method="post">
        username: <input type="text" name="username"/>
        <input type="submit" value="Submit"/>
    </form>

</center>

</body>
</html>
