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
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="/main">
                    <span class="glyphicon glyphicon-home"></span>
                    Home</a>
                </li>
                <li><a href="/settings"> Settings </a></li>
                <li><a href="/upload"> Uploads </a></li>

                <c:if test="${user.role == 'admin'}">
                    <li><a href="/unchecked-books"> New books for processing </a></li>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="/main">
                        <span class="glyphicon glyphicon-user"></span>
                        <c:out value="${user.firstName} "/>
                        <c:out value="${user.lastName}"/></a>
                </li>
                <li><a href="/"> Exit </a></li>
            </ul>
        </div>
    </div>
</nav>

<script type="text/javascript"
        src="/webjars/jquery/3.2.1/jquery.min.js"></script>

<script type="text/javascript"
        src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>