<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Books</title>
</head>
<script src="${pageContext.request.contextPath}/scripts/jquery-3.2.1.js"></script>
<script type="text/javascript">
    $(function(){
        $("#pageNo").change(function(){
            var val = $(this).val();
            val = $.trim(val);

            //1. 校验 val 是否为合法
            var flag = false;
            var reg = /^\d+$/g;
            var pageNo = 0;

            if(reg.test(val)){
                //2. 校验 val 在一个合法的范围内： 1-totalPageNumber
                pageNo = parseInt(val);
                if(pageNo >= 1 && pageNo <= parseInt("${bookPage.totalPageNumber }")){
                    flag = true;
                }
            }

            if(!flag){
                alert("输入的不是合法的页码.");
                $(this).val("");
                return;
            }

            //3. 页面跳转
            var href = "getBooks.do?pageNo=" + pageNo + "&" + $(":hidden").serialize();
            window.location.href = href;
        });
    })
</script>
<%@ include file="/commons/common.jsp" %>
<%@ include file="/commons/queryCondition.jsp" %>
<body>

<center>
    <c:if test="${param.title != null}">
        您已经将《 ${param.title} 》放入到购物车中.
        <br><br>
    </c:if>

    <c:if test="${!empty sessionScope.ShoppingCart.books}">
        您的购物车中有 ${sessionScope.ShoppingCart.bookNumber} 本书,
        <a href="forwardPage.do?page=cart&pageNo=${bookPage.pageNo}">查看购物车</a>
    </c:if>
    <br><br>

    <form action="getBooks.do" method="post">
        Price:
        <input type="text" size="1" name="minPrice">
        -
        <input type="text" size="1" name="maxPrice">
        <input type="submit">
    </form>
    <br><br>

    <table cellpadding="10">
        <c:forEach items="${bookPage.list}" var="book">
            <tr>
                <td>
                    《<a href="getBook.do?pageNo=${bookPage.pageNo}&id=${book.id}">${book.title}</a>》
                    <br>
                    作者:${book.author}
                </td>
                <td>${book.price}</td>
                <td><a href="addToCart.do?pageNo=${bookPage.pageNo}&id=${book.id}&title=${book.title}">加入购物车</a></td>
            </tr>
        </c:forEach>
    </table>
    <br><br>

    共 ${bookPage.totalPageNumber} 页
    &nbsp;&nbsp;
    当前第 ${bookPage.pageNo} 页
    &nbsp;&nbsp;

    <c:if test="${bookPage.hasPrev}">
        <a href="getBooks.do?pageNo=1">首页</a>
        &nbsp;&nbsp;
        <a href="getBooks.do?pageNo=${bookPage.prevPage}">上一页</a>
    </c:if>
    &nbsp;&nbsp;

    <c:if test="${bookPage.hasNext}">
        <a href="getBooks.do?pageNo=${bookPage.nextPage}">下一页</a>
        &nbsp;&nbsp;
        <a href="getBooks.do?pageNo=${bookPage.totalPageNumber}">末页</a>
    </c:if>
    &nbsp;&nbsp;

    转到 <input type="text" size="1" id="pageNo"/> 页
</center>

</body>
</html>
