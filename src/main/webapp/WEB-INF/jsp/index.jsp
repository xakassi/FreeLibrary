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
            <a class="navbar-brand" href="/">Free Library</a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row content">
        <div class="col-sm-3">
            <spring:form method="post" modelAttribute="user" class="form-sign" action="/check-user">
                <h1>Welcome!</h1>
                <div class="form-group">
                    <label class="control-label">Login:</label>
                    <spring:input path="login" class="form-control" placeholder="Username"/>
                </div>
                <div class="form-group">
                    <label class="control-label">Password:</label>
                    <spring:password path="passwordHash" class="form-control" placeholder="Password"/>
                </div>

                <spring:button class="btn btn-success btn-block">Sign in</spring:button>

            </spring:form>

            <span style="color: red; "><c:out value="${message}"/></span>
            <br/><br/>
        </div>

        <div class="col-sm-3">
            <img src="<c:url value="/img/library.png"/>" width="280px" height="280px">
        </div>

        <div class="col-sm-3">
            <spring:form method="get" modelAttribute="user" action="/reg-user">
                <h2>For the first time on our site?</h2>
                <spring:button class="btn btn-primary">Sign up</spring:button>
            </spring:form>
        </div>


    </div>
</div>

</body>

</html>