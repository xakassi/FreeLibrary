<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Free Library</title>
</head>
<body>

<spring:form method="post" modelAttribute="user" action="/check-login">

    <h2>Registration</h2>
    <table>
        <tr>
            <td> First name:</td>
            <td><spring:input path="firstName"/></td>
        </tr>
        <tr>
            <td> Surname:</td>
            <td><spring:input path="lastName"/></td>
        </tr>
        <tr>
            <td> Login:</td>
            <td><spring:input path="login"/></td>
        </tr>
        <tr>
            <td> Password:</td>
            <td><spring:password path="passwordHash"/></td>
        </tr>
        <tr>
            <td> Confirm password:</td>
            <td><input type="password" id="password" name="password"/></td>
        </tr>
        <tr>
            <td><spring:button>Sign up</spring:button></td>
        </tr>
    </table>
</spring:form>

<c:if test="${success == 'no'}">
    <span style="color: red; "><c:out value="${message}"/></span>
</c:if>
<c:if test="${success == 'yes'}">
    <span style="color: green; "><c:out value="${message}"/></span> <br>
    <a href="/"> Continue </a>
</c:if>

</body>
</html>
