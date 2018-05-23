<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <link rel="stylesheet" type="text/css"
          href="webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <c:url value="/css/main.css" var="jstlCss"/>
    <link href="${jstlCss}" rel="stylesheet"/>

    <title>Free Library</title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand"  href="/">Free Library</a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="starter-template">

        <spring:form method="post" modelAttribute="user" action="/check-user">
            <h1>Welcome!</h1>
            <table>
                <tr>
                    <td> Login:</td>
                    <td><spring:input path="login"/></td>
                </tr>
                <tr>
                    <td> Password:</td>
                    <td><spring:password path="passwordHash"/></td>
                </tr>
                <tr>
                    <td><spring:button>Sign in</spring:button></td>
                </tr>
            </table>
        </spring:form>

        <span style="color: red; "><c:out value="${message}"/></span>
        <br/><br/>

        <img src="<c:url value="/img/library.png"/>" width="200px" height="200px">

        <spring:form method="get" modelAttribute="user" action="/reg-user">
            <h2>For the first time on our site?</h2>
            <spring:button>Sign up</spring:button>
        </spring:form>

    </div>
</div>

</body>

</html>