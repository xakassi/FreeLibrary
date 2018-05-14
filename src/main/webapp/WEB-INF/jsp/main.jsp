<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Free Library</title>
</head>
<body>
    Free Library | <c:out value = "${user.firstName}"/> <c:out value = "${user.lastName}"/>  |
    <a href="/settings"> Settings </a> | <a href="/upload"> Uploads </a> |
    <c:if test="${user.role == 'admin'}">
        <a href="/unchecked-books"> New books for processing </a> |
    </c:if>
    <a href="/"> Exit </a> <br/><br/>

    <spring:form method="get" modelAttribute="bookRequest" action="/search-book">
        Please, fill in the required fields for search:
        <table>
            <tr>
                <td> Book name: </td>
                <td> <spring:input path="name"
                    placeholder="Input book name..." size="70"/>
                </td>
            </tr>
            <tr>
                <td> Author: </td>
                <td> <spring:input path="author"
                    placeholder="Input author surname..." size="70"/>
                </td>
            </tr>
            <tr>
                <td> Genre: </td>
                <td>
                    <spring:select path="genre">
                        <spring:option value="Choose a genre" label="Choose a genre" />
                        <c:if test="${not empty genres}">
                            <c:forEach var="g" items="${genres}">
                                <spring:option value="${g}" label="${g}" />
                            </c:forEach>
                        </c:if>
                    </spring:select>
                </td>
            </tr>
            <tr>
                <td> Category: </td>
                <td>
                    <spring:select path="category">
                        <spring:option value="Choose a category" label="Choose a category" />
                        <c:if test="${not empty categories}">
                            <c:forEach var="c" items="${categories}">
                                <spring:option value="${c}" label="${c}" />
                            </c:forEach>
                        </c:if>
                    </spring:select>
                </td>
            </tr>
            <tr>
                <td> <input type="submit" value="Search"/> </td>
            </tr>
        </table>
    </spring:form>

    <c:if test="${success == 'no'}">
        <span style="color: red; "><c:out value = "${message}"/></span>
    </c:if>
    <c:if test="${success == 'yes'}">
        <span style="color: green; "><c:out value = "${message}"/></span> <br>
    </c:if>

        <div>
            <c:if test="${not empty books}">
                <ul>
                    <c:forEach var="b" items="${books}">
                        <a href="/book-info?getId=${b.id}"> <c:out value = "${b}"/> </a><br/>
                    </c:forEach>
                </ul>
            </c:if>
        </div>


</body>

</html>