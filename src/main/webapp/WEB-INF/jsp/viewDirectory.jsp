<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
<head>
    <title>Remote File Manager</title>
<%--
    <link type="text/css" rel="stylesheet" href="${ctx}/styles.css" />
    <c:set var="context" value="${pageContext.request.contextPath}"/>
    <link type="text/css" rel="stylesheet" href="${context}/styles.css" />
--%>
    <link type="text/css" rel="stylesheet" href="<c:url value="/styles.css" />" />
<%--    <link type="text/css" rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/styles.css" />" />--%>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <table bgcolor="#FFFFFF" width="800" align="center">

    <%--append file deleted message--%>
    <c:if test="${sessionScope.deleted != null && sessionScope.deleted != ''}">
        <tr>
            <td>
                <div class="message">${sessionScope.deleted}</div>
            </td>
        </tr>
        ${sessionScope.deleted = ""}
    </c:if>

    <%--append file created message--%>
    <c:if test="${sessionScope.created != null && sessionScope.created != ''}">
        <tr>
            <td>
                <div class="message">${sessionScope.created}</div>
            </td>
        </tr>
        ${sessionScope.created = ""}
    </c:if>

    <%--"back" link--%>
    <c:if test="${fn:startsWith(requestScope.backPath, requestScope.root)}">
        <tr>
            <td>
                <form name="back" method="post" action="/dir/view">
                    <input type="submit" value="Back">
                    <input type="hidden" name="path" value="${requestScope.backPath}">
                </form>
            </td>
        </tr>
    </c:if>

    <%--append "New File" menu--%>
    <tr>
        <td colspan="2">
            <div class="menu">
                <form name="menu" method="post" action="/file/create">
                    <input type="text" name="filename" />
                    <input type="submit" value="Create New File" />
                    <%--<input type="hidden" name="path" value="${sessionScope.path}" />--%>
                </form>
            </div>
        </td>
    </tr>


    <%--append current path--%>
    <tr>
        <td colspan="2">
            <div class="path">
                ${sessionScope.path}
            </div>
        </td>
    </tr>

    <%--append current path--%>
    <c:forEach items="${dirLinks}" var="link">
        <tr>
            <td>
                <div class="row">
                    <form name="main" method="post" action="/dir/view">
                        <input type="submit" name="text" value="${link.key}" class="dir">
                        <input type="hidden" name="path" value="${link.value}">
                    </form>
                </div>
            </td>
            <td>
                <div class="row">
                    <form name="del" method="post" action="/file/remove">
                        <input type="submit" value="delete" class="del">
                        <input type="hidden" name="path" value="${link.value}">
                    </form>
                </div>
            </td>
        </tr>
    </c:forEach>

     <c:forEach items="${fileLinks}" var="link">
         <tr>
             <td>
                 <div class="row">
                     <form name="main" method="post" action="/file/view">
                         <input type="submit" name="text" value="${link.key}" class="file">
                         <input type="hidden" name="path" value="${link.value}">
                     </form>
                 </div>
             </td>
             <td>
                 <div class="row">
                     <form name="del" method="post" action="/file/remove">
                         <input type="submit" value="delete" class="del">
                         <input type="hidden" name="path" value="${link.value}">
                     </form>
                 </div>
             </td>
         </tr>
     </c:forEach>

    </table>
    <jsp:include page="footer.jsp"/>
</body>
</html>
