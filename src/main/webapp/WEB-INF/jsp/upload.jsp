<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<html>

<head>
</head>

<body>
<%@ include file="menu.jsp" %>
<div class="col-xs-4">
    <spring:form method="POST" modelAttribute="uncheckedBook" action="/upload-file"
                 enctype="multipart/form-data">

        You can upload your book to the library.<br>
        Administrators will check your book
        and add it to library if it is ok and there is no such a book yet.
        <br><br>

        <div class="form-group">
            <label class="control-label">Author:</label>
            <spring:input path="author" class="form-control"
                          placeholder="You can input an author surname..."/>
        </div>
        <div class="form-group">
            <label class="control-label">Description:</label>
            <spring:input path="description" class="form-control"
                          placeholder="You can input a little description..."/>
        </div>

        <div class="form-group">
            <label class="btn btn-primary" for="my-file-selector">
                <input id="my-file-selector" name="file" type="file" style="display:none"
                       onchange="$('#upload-file-info').html(this.files[0].name)">
                Browse
            </label>
            <span class='label label-info' id="upload-file-info"></span>

        </div>
        <div class="form-group">
            <input type="submit" value="Confirm" class="btn btn-success">
        </div>

    </spring:form>

    <c:if test="${success == 'no'}">
        <span style="color: red; "><c:out value="${message}"/></span> <br>
    </c:if>
    <c:if test="${success == 'yes'}">
        <span style="color: green; "><c:out value="${message}"/></span> <br>
    </c:if>
</div>

<div class="col-xs-4">
    <div class="jumbotron">
        <div class="form-group">

            <h3>
                <p class="text-center">
                    <span class="glyphicon glyphicon-pushpin"></span>
                    Your notifications</p>

            </h3>
            <c:if test="${not empty notifs}">
                <spring:form method="get" action="/delete-notif">
                    <div class="form-group">
                        <button type="submit" class="btn btn-danger pull-right">
                            <span class="glyphicon glyphicon-trash"></span> Delete notification
                        </button>
                    </div>
                    <div class="form-group">
                        <ul>
                            <c:forEach var="n" items="${notifs}">
                                <li>
                                    <label>
                                        <input type="radio" checked name="notifID"
                                               value="${n.id}"/>
                                            ${n.notice}
                                        <br>
                                        <span class="label label-info">${n.date}</span>
                                    </label>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </spring:form>
            </c:if>
            <c:if test="${empty notifs}">
                <p class="text-center">
                    <em>You have not any notifications.</em>
                </p>
            </c:if>
        </div>
    </div>
</div>
</div>
</body>
</html>