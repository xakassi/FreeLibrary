<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
</head>

<body>
<%@ include file="menu.jsp" %>

<div class="container">
    <ul class="nav nav-tabs">
        <h2> Settings </h2>

        <li class="active"><a data-toggle="tab" href="#change">Change parameters</a></li>
        <li><a data-toggle="tab" href="#delete">Delete</a></li>
        <c:if test="${user.role == 'admin'}">
            <li><a data-toggle="tab" href="#addAdmin">Admin</a></li>
        </c:if>
    </ul>

    <div class="tab-content">
        <div id="change" class="tab-pane fade in active">
            <div class="col-xs-4">
                <h3> You can change your parameters </h3>
                <spring:form method="post" modelAttribute="changeUser" action="/change-user-parameters">

                    <div class="form-group">
                        <label class="control-label">First name:</label>
                        <spring:input path="firstName" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Surname:</label>
                        <spring:input path="lastName" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Password:</label>
                        <spring:password path="passwordHash" class="form-control"
                                         placeholder="Enter new password..."/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Confirm password:</label>
                        <input type="password" id="password" name="password" class="form-control"
                               placeholder="Repeat new password..."/>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Enter your old password for confirm:</label>
                        <input type="password" id="passwordOld" name="passwordOld"
                               class="form-control"
                               placeholder="Enter your old password..."/>
                    </div>

                    <input type="submit" class="btn btn-primary"
                           value="Change parameters"/>

                </spring:form>
            </div>

            <c:if test="${success == 'no'}">
                <span style="color: red; "><c:out value="${message}"/></span>
            </c:if>
            <c:if test="${success == 'yes'}">
                <span style="color: green; "><c:out value="${message}"/></span> <br>
                <a href="/"> Continue </a>
            </c:if>

        </div>


        <div id="delete" class="tab-pane fade">
            <c:if test="${user.login != 'mainAdmin'}">
                <spring:form method="get" action="/delete-user">
                    <h3> Account deleting </h3>
                    Delete your account?<br>
                    <span style="color: red; ">You will not be able to undo this action! </span>
                    <br><br>
                    <input type="submit" value="Delete" class="btn btn-danger"/>
                </spring:form>
            </c:if>
            <c:if test="${user.login == 'mainAdmin'}">
                <h2>You can not delete your account!</h2>
            </c:if>
        </div>


        <div id="addAdmin" class="tab-pane fade">
            <c:if test="${user.role == 'admin'}">
                <spring:form method="get" action="/reg-admin">
                    <h3> Registration </h3>
                    You can register another users with administrator rights. <br>
                    You should fill the form for them. <br><br>
                    <input type="submit" value="Show form" class="btn btn-primary"/>
                </spring:form>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
