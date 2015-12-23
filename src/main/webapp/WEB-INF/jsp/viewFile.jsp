<%@ page import="java.nio.file.Paths" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>File View</title>
</head>
<body>

    <%--"back" link--%>
    <form name="back" method="post" action="/dir/view">
        <input type="submit" value="Back">
        <input type="hidden" name="path" value="<%= Paths.get((String) session.getAttribute("path")).getParent()%>">
    </form>

</body>
</html>
