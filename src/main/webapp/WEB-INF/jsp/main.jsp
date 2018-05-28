<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
</head>
<body>
<%@ include file="menu.jsp" %>

<spring:form method="get" modelAttribute="bookRequest" action="/search-book">

    <div class="col-xs-4">

        <p class="bg-primary">Please, fill in the required fields for search</p>

        <div class="form-group">
            <label class="control-label">Book name:</label>
            <spring:input path="name" placeholder="Input book name..."
                          class="form-control" size="70"/>
        </div>
        <div class="form-group"><label class="control-label">Author:</label>
            <spring:input path="author" placeholder="Input author surname..."
                          class="form-control" size="70"/>
        </div>
        <div class="form-group"><label class="control-label">Genre:</label>
            <spring:select path="genre" class="form-control">
                <spring:option value="Choose a genre" label="Choose a genre"/>
                <c:if test="${not empty genres}">
                    <c:forEach var="g" items="${genres}">
                        <spring:option value="${g}" label="${g}"/>
                    </c:forEach>
                </c:if>
            </spring:select>
        </div>
        <div class="form-group"><label class="control-label">Category:</label>
            <spring:select path="category" class="form-control">
                <spring:option value="Choose a category" label="Choose a category"/>
                <c:if test="${not empty categories}">
                    <c:forEach var="c" items="${categories}">
                        <spring:option value="${c}" label="${c}"/>
                    </c:forEach>
                </c:if>
            </spring:select>
        </div>

        <input type="submit" class="btn btn-primary" value="Search"/>
    </div>
</spring:form>

<c:if test="${success == 'no'}">
    <span style="color: red; "><c:out value="${message}"/></span>
</c:if>
<c:if test="${success == 'yes'}">
    <span style="color: green; "><c:out value="${message}"/></span> <br>
</c:if>

<div>
    <c:if test="${not empty books}">
        <ul>
            <c:forEach var="b" items="${books}">
                <a href="/book-info?getId=${b.id}"> <c:out value="${b}"/> </a><br/>
            </c:forEach>
        </ul>
    </c:if>
</div>


</body>

</html>