<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<%
    response.sendRedirect(request.getContextPath() + "/getBooks.do");
%>
</body>
</html>
