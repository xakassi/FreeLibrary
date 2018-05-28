<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
</head>

<body>
<%@ include file="menu.jsp" %>

<div class="col-xs-4">
    <spring:form method="post" modelAttribute="user" action="/check-login" class="form-sign">

        <h2>Registration</h2>

        <div class="form-group">
            <label class="control-label">First name:</label>
            <spring:input path="firstName" class="form-control"/>
        </div>
        <div class="form-group">
            <label class="control-label">Surname:</label>
            <spring:input path="lastName" class="form-control"/>
        </div>
        <div class="form-group">
            <label class="control-label">Login:</label>
            <spring:input path="login" class="form-control"/>
        </div>
        <div class="form-group">
            <label class="control-label">Password:</label>
            <spring:password path="passwordHash" class="form-control"/>
        </div>
        <div class="form-group">
            <label class="control-label">Confirm password:</label>
            <input type="password" id="password" name="password" class="form-control"/>
        </div>

        <spring:button class="btn btn-success btn-block">Sign up</spring:button>
    </spring:form>

    <c:if test="${success == 'no'}">
        <span style="color: red; "><c:out value="${message}"/></span>
    </c:if>
    <c:if test="${success == 'yes'}">
        <span style="color: green; "><c:out value="${message}"/></span> <br>
        <a href="/"> Continue </a>
    </c:if>

</div>

</body>
</html>
