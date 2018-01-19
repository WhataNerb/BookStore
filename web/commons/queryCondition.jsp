<%--
  Created by IntelliJ IDEA.
  User: 党昊天
  Date: 2018/1/18 0018
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">

    $(function(){
        $("a").each(function(){
            this.onclick = function(){
                var serializeVal = $(":hidden").serialize();
                var href = this.href + "&" + serializeVal;
                window.location.href = href;
                return false;
            };
        });
    });

</script>

<input type="hidden" name="minPrice" value="${param.minPrice }"/>
<input type="hidden" name="maxPrice" value="${param.maxPrice }"/>
