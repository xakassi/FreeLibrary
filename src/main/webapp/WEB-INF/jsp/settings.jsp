<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Free Library</title>
</head>
<body>
    Free Library | <c:out value = "${user.firstName}"/> <c:out value = "${user.lastName}"/> |
    <a href="/main"> Main page </a> | <a href="/"> Exit </a>
    <br/>

    <spring:form method="post" modelAttribute="changeUser" action="/change-user-parameters">
        <h2> Settings </h2>
        <h3> You can change your parameters </h3>
        <table>
            <tr>
                <td> First name: </td>
                 <td> <spring:input path="firstName"/> </td>
            </tr>
            <tr>
                <td> Surname: </td>
                <td> <spring:input path="lastName"/> </td>
            </tr>
            <tr>
                <td> Password: </td>
                <td> <spring:password path="passwordHash"
                    placeholder="Enter new password..."/> </td>
            </tr>
            <tr>
                <td> Confirm password: </td>
                <td> <input type="password" id="password" name="password"
                    placeholder="Repeat new password..."/> </td>
            </tr>
            <tr>
                <td> Enter your old password for confirm: </td>
                <td> <input type="password" id="passwordOld" name="passwordOld"
                    placeholder="Enter your old password..."/> </td>
            </tr>
            <tr>
                <td> <input type="submit" value="Change parameters"/> </td>
            </tr>
        </table>
    </spring:form>

    <c:if test="${success == 'no'}">
        <span style="color: red; "><c:out value = "${message}"/></span>
    </c:if>
    <c:if test="${success == 'yes'}">
        <span style="color: green; "><c:out value = "${message}"/></span> <br>
        <a href="/"> Continue </a>
    </c:if>

    <c:if test="${user.login != 'mainAdmin'}">
        <spring:form method="get" action="/delete-user">
            <h3> Account deleting </h3>
            Delete your account?<br>
            <span style="color: red; ">You will not be able to undo this action! </span> <br>
            <input type="submit" value="Delete"/>
        </spring:form>
    </c:if>

    <c:if test="${user.role == 'admin'}">
        <spring:form method="get" action="/reg-admin">
            <h3> Registration </h3>
            You can register another users with administrator rights. <br>
            You should fill the form for them. <br>
            <input type="submit" value="Show form"/>
        </spring:form>
    </c:if>

</body>
</html>
