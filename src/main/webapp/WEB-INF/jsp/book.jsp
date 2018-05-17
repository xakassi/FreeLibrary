<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<html>

<head>
    <title>Free Library</title>
</head>
<body>
Free Library | <c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/> |
<a href="/main"> Main page </a> | <a href="/"> Exit </a>
<br/><br/>

<spring:form method="get" modelAttribute="book" action="/download-book">
    <input type="hidden" name="bookID" value="${book.id}"/>
    <table>
        <tr>
            <td> Book title:</td>
            <td><c:out value="${book.name}"/></td>
        </tr>
        <tr>
            <td> Author:</td>
            <td>
                <c:if test="${not empty book.author.firstName}">
                    <c:out value="${book.author.firstName}"/>
                </c:if>
                <c:if test="${not empty book.author.secondName}">
                    <c:out value="${book.author.secondName}"/>
                </c:if>
                <c:out value="${book.author.lastName}"/>
            </td>
        </tr>
        <tr>
            <td> Genre:</td>
            <td> ${book.genre} </td>
        </tr>
        <tr>
            <td> Category:</td>
            <td> ${book.category} </td>
        </tr>
        <tr>
            <c:if test="${not empty book.description}">
                <td> Description:</td>
                <td><c:out value="${book.description}"/></td>
            </c:if>
        </tr>
        <tr>
            <td><input type="submit" value="Download"/></td>
        </tr>
    </table>
</spring:form>

<c:if test="${user.role == 'admin'}">
    <spring:form method="get" modelAttribute="book" action="/delete-book">
        <input type="hidden" name="bookID" value="${book.id}"/>
        Do you want to delete this book? <br>
        <span style="color: red; ">You will not be able to undo this action! </span> <br>
        <input type="submit" value="Delete"/>
    </spring:form>
</c:if>

</body>
</html>