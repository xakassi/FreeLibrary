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

<spring:form method="POST" modelAttribute="uncheckedBook" action="/upload-file" enctype="multipart/form-data">
    <table>
        <tr>
            <td> You can upload your book to the library.</td>
        </tr>
        <tr>
            <td> Administrators will check your book
                and add it to library if it is ok and there is no such a book yet.
            </td>
        </tr>
    </table>
    <table>
        <tr>
            <td> Author:</td>
            <td><spring:input path="author"
                              placeholder="You can input an author surname..." size="70"/>
            </td>
        </tr>
        <tr>
            <td> Description:</td>
            <td><spring:input path="description"
                              placeholder="You can input a little description..." size="70"/>
            </td>
        </tr>
    </table>
    <table>
        <tr>
            <td><input type="file" name="file"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Confirm"></td>
        </tr>
    </table>
</spring:form>

<c:if test="${success == 'no'}">
    <span style="color: red; "><c:out value="${message}"/></span> <br>
</c:if>
<c:if test="${success == 'yes'}">
    <span style="color: green; "><c:out value="${message}"/></span> <br>
</c:if>

<div>
    <c:if test="${not empty notifs}">
        *************** <br>
        Your notifications <br>
        *************** <br>
        <spring:form method="get" action="/delete-notif">
            <input type="submit" value="Delete notification">
            <ul>
                <c:forEach var="n" items="${notifs}">
                    <li>
                        <label>
                            <input type="radio" checked name="notifID"
                                   value=
                                       <c:out value="${n.id}"/>/>
                                ${n.notice}
                            <br>
                                ${n.date}
                        </label>
                    </li>
                </c:forEach>
            </ul>
        </spring:form>
    </c:if>
</div>

</body>
</html>