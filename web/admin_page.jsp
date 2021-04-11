<%-- 
    Document   : admin_page
    Created on : Feb 4, 2021, 5:34:40 PM
    Author     : quocl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style><%@include file="/css/admin_page.css"%></style>
        <title>Admin Page</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.LOGIN_USER}"/>
        <c:if test="${user.getRole() != 'admin'}">
            <c:redirect url="logout"></c:redirect>
        </c:if>

        <nav class="navbar navbar-expand-md navbar-light bg-light sticky-top">
            <div class="container-fluid">
                <h1 class="nav justify-content-left" style="color: red">Administrator Page</h1>
                <form action="logout" method="POST">
                    Welcome, ${user.getName()}! <input type="submit" class="btn btn-outline-secondary" value="Logout"/>
                </form> 
            </div>
        </nav>

        <div id="add-new-button">
            <form action="newQuestion" method="POST">
                <input type="submit" class="btn btn-outline-primary" value="Add new question"/>
            </form>
        </div> <br/> <br/>

        <div class="container-fluid padding">
            <div class="row welcome text-center">
                <div class="col-12">
                    <h2 class="display-6">Search Question</h2>
                </div>
                <div id="search-form">
                    <form action="searchQuestion" method="POST">
                        <div class="form-inline">
                            <select class="form-control" name="cbxSubject">
                                <option <c:if test="${sessionScope.SUBJECT == 'All Subject'}">selected</c:if>>All Subject</option>
                                <option <c:if test="${sessionScope.SUBJECT == 'Prj311- Java Desktop'}">selected</c:if>>Prj311- Java Desktop</option>
                                <option <c:if test="${sessionScope.SUBJECT == 'Prj321- Java Web'}">selected</c:if>>Prj321- Java Web</option>
                            </select>
                            <select class="form-control" name="cbxStatus">
                                <option <c:if test="${sessionScope.STATUS == 'All Status'}">selected</c:if>>All Status</option>
                                <option <c:if test="${sessionScope.STATUS == 'Active'}">selected</c:if>>Active</option>
                                <option <c:if test="${sessionScope.STATUS == 'DeActive'}">selected</c:if>>DeActive</option>
                            </select>
                        </div>
                        <div class="form-inline">
                            <input type="text" class="form-control" placeholder="Question" name="txtSearchValue" value="${sessionScope.SEARCH_VALUE}"/>
                            <input type="submit" class="btn btn-outline-secondary" value="Search"/>
                            <input type="hidden" name="isClickSearch" value="true" />
                        </div>
                    </form>
                </div>
            </div>
            <hr>
        </div>
                            
        <c:if test="${not empty requestScope.UPDATE_SUCCESS}">
            <div class="alert alert-success alert-dismissible fade show" role="alert" style="margin: 0 auto; width: 70%; text-align: center;">
                ${requestScope.UPDATE_SUCCESS}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <c:set var="questionsList" value="${sessionScope.QUESTION_LIST}"/>
        <c:if test="${not empty questionsList}">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th scope="col">No.</th>
                        <th scope="col">Question Content</th>
                        <th scope="col">Answer Content</th>
                        <th scope="col">Correct Answer</th>
                        <th scope="col">Subject</th>
                        <th scope="col">Status</th>
                        <th scope="col">Update</th>
                        <th scope="col">Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="questions" items="${questionsList}" varStatus="counter">
                    <form action="doAction" method="POST">
                        <tr>
                            <td>${counter.count}</td>
                            <td width="40%">${questions.getQuestion_content()}</td>
                            <td width="15%">
                                <c:set var="answer" value="${fn:split(questions.getAnswer_content(), 'Ñ')}"/>
                                <c:out value="A. ${answer[0]}"/><br/>
                                <c:out value="B. ${answer[1]}"/><br/>
                                <c:out value="C. ${answer[2]}"/><br/>
                                <c:out value="D. ${answer[3]}"/>
                            </td>
                            <td width="15%">${questions.getAnswer_correct()}</td>
                            <td width="15%">${questions.getSubject()}</td>
                            <td>${questions.getStatus()}</td>
                            <td>
                                <input type="submit" class="btn btn-outline-success" name="update" value="Update"/>
                            </td>
                            <td>
                                <button type="submit" class="btn btn-outline-danger" name="delete" value="Delete" onclick="return confirm('Do you want to delete this question?')">
                                    Delete
                                </button>
                            </td>
                        <input type="hidden" name="questionID" value="${questions.getQuestion_id()}" />
                        <input type="hidden" name="page" value="${requestScope.CURRENT_PAGE}" />
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>

        <nav>
            <ul class="pagination nav justify-content-center">
                <c:if test="${requestScope.CURRENT_PAGE != 1}">
                    <li class="page-item"><a class="page-link" href="searchQuestion?page=${requestScope.CURRENT_PAGE - 1}">Previous</a></li>
                    </c:if>
                    <c:forEach var="i" begin="1" end="${requestScope.NO_OF_PAGE}">
                        <c:choose>
                            <c:when test="${requestScope.CURRENT_PAGE == i}">
                            <li class="page-item active"><a class="page-link" href="#">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                            <li class="page-item"><a class="page-link" href="searchQuestion?page=${i}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${requestScope.CURRENT_PAGE != requestScope.NO_OF_PAGE}">
                    <li class="page-item"><a class="page-link" href="searchQuestion?page=${requestScope.CURRENT_PAGE + 1}">Next</a></li>
                    </c:if>
            </ul>
        </nav>
    </c:if>
</body>
</html>
