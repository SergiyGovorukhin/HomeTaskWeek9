<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>File View</title>
    <link type="text/css" rel="stylesheet" href="${context}/css/styles.css" />
</head>
<body>
    <jsp:include page="header.jsp"/>
    <%--"back" link--%>
    <div>
        <form name="back" method="post" action="${context}/dir/view">
            <input type="submit" value="Back">
            <input type="hidden" name="path" value="${requestScope.backPath}">
        </form>
    </div>
    <div>
        File: ${requestScope.filename}
    </div>
    <section>
        <h3>Contents:</h3>
        <c:forEach items="${lines}" var="line">
            ${line} <br>
        </c:forEach>
    </section>
    <jsp:include page="footer.jsp"/>
</body>
</html>
