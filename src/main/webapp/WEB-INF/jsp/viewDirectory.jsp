<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Remote File Manager</title>
    <link type="text/css" rel="stylesheet" href="${context}/css/styles.css" />
</head>
<body>
    <jsp:include page="header.jsp"/>

    <%--append file opened message--%>
    <c:if test="${sessionScope.opened != null && sessionScope.opened != ''}">
        <div class="message">${sessionScope.opened}</div><br>
        ${sessionScope.opened = ""}
    </c:if>

    <%--append file deleted message--%>
    <c:if test="${sessionScope.deleted != null && sessionScope.deleted != ''}">
        <div class="message">${sessionScope.deleted}</div><br>
        ${sessionScope.deleted = ""}
    </c:if>

    <%--append file created message--%>
    <c:if test="${sessionScope.created != null && sessionScope.created != ''}">
        <div class="message">${sessionScope.created}</div><br>
        ${sessionScope.created = ""}
    </c:if>

    <table>
        <%--"back" link--%>
        <c:if test="${fn:startsWith(requestScope.backPath, requestScope.root)}">
            <tr>
                <td>
                    <form name="back" method="post" action="${context}/dir/view">
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
                    <form name="menu" method="post" action="${context}/file/create">
                        <input type="text" name="filename" />
                        <input type="submit" value="Create File" />
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
                        <form name="main" method="post" action="${context}/dir/view">
                            <input type="submit" name="text" value="[${link.key}]" class="dir">
                            <input type="hidden" name="path" value="${link.value}">
                        </form>
                    </div>
                </td>
                <td>
                    <div class="row">
                        <form name="del" method="post" action="${context}/file/remove">
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
                        <form name="main" method="post" action="${context}/file/view">
                            <input type="submit" name="text" value="${link.key}">
                            <input type="hidden" name="path" value="${link.value}">
                        </form>
                    </div>
                </td>
                <td>
                    <div class="row">
                        <form name="del" method="post" action="${context}/file/remove">
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
