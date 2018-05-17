<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Free Library</title>
</head>
<body>
Free Library | <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/> |
<a href="/settings"> Settings </a> | <a href="/upload"> Uploads </a> |
<a href="/main"> Main page </a> |
<a href="/"> Exit </a> <br/><br/>

<spring:form method="get" action="/unch-book-info">
    <c:if test="${unchNum == 0}">
        There are no unchecked books in the queue. <br>
        <a href="/main"> Continue </a>
    </c:if>
    <c:if test="${unchNum != 0}">
        There are <c:out value="${unchNum}"/> unchecked books in the queue. <br>
        Take an unchecked book for processing. <br>
        <div>
            <c:if test="${not empty unchBooks}">
                <ul>
                    <c:forEach var="b" items="${unchBooks}">
                        <a href="/unch-book-info?getId=${b.id}"> ${b} </a><br/>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </c:if>
</spring:form>

<c:if test="${success == 'no'}">
    <span style="color: red; "><c:out value="${message}"/></span>
</c:if>
<c:if test="${success == 'yes'}">
    <span style="color: green; "><c:out value="${message}"/></span> <br>
</c:if>


</body>

</html>