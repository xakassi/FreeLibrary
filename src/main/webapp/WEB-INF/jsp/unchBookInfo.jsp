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
    Free Library | <c:out value = "${user.firstName}"/> <c:out value = "${user.lastName}"/> | <a href="/"> Exit </a>
    <br/><br/>

        Check the file <a href="/check-file/${unchBook.id}"> ${unchBook.bookName} </a><br/>
        If it is ok and you want to add this book to the library,
        fill all fields and choose 'Accept' button. <br>
        If it is not ok and you do not want to add this book to the library,
        just choose the 'Refuse' button. <br>

    <spring:form method="post" modelAttribute="book" action="/accept-new-book">
        <input type="hidden" name="unchBookID" value="${unchBook.id}"/>
        <table>
            <tr>
                <td> Book name: </td>
                <td> <spring:input path="name"
                    placeholder="Input book name..." size="70"/>
                </td>
            </tr>
            <tr>
                <td> Author surname: </td>
                <td> <spring:input path="author.lastName"
                    placeholder="Input author surname..." size="70"/>
                </td>
            </tr>
            <tr>
                <td> Author first name: </td>
                <td> <spring:input path="author.firstName"
                    placeholder="Input author first name..." size="70"/>
                </td>
            </tr>
            <tr>
                <td> Author patronymics: </td>
                <td> <spring:input path="author.secondName"
                    placeholder="Input author patronymics..." size="70"/>
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
                <td> <input type="submit" value="Accept"/> </td>
            </tr>
        </table>
    </spring:form>

    <spring:form method="post" action="/refuse-new-book">
        <input type="hidden" name="unchBookID" value="${unchBook.id}"/>
        <input type="submit" value="Refuse"/>
    </spring:form>

    <c:if test="${success == 'no'}">
        <span style="color: red; "><c:out value = "${message}"/></span>
    </c:if>
    <c:if test="${success == 'yes'}">
        <span style="color: green; "><c:out value = "${message}"/></span> <br>
    </c:if>


</body>
</html>