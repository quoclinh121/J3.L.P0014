<%-- 
    Document   : student_page
    Created on : Feb 11, 2021, 12:30:55 PM
    Author     : quocl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style><%@include file="/css/student_page.css"%></style>
        <title>Student Page</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.LOGIN_USER}"/>
        <c:if test="${user.getRole() != 'student'}">
            <c:redirect url="logout"></c:redirect>
        </c:if>

        <nav class="navbar navbar-expand-md navbar-light bg-light sticky-top">
            <div class="container-fluid">
                <h1 class="nav justify-content-left" style="color: red">Student Page</h1>
                <form action="logout" method="POST">
                    Welcome, ${user.getName()}! <input type="submit" class="btn btn-outline-secondary" value="Logout"/>
                </form> 
            </div>
        </nav> <br/> <br/>

        <div class="container-fluid padding">
            <div class="row welcome text-center">
                <div class="col-12">
                    <h2 class="display-6">Quiz Online</h2>
                </div>
                <div id="select-quiz-form">
                    <form action="doQuiz" method="POST">
                        <div class="form-inline">
                            <label>Choose subject: </label>
                            <select class="form-control" name="cbxSubject">
                                <option>Prj311- Java Desktop</option>
                                <option>Prj321- Java Web</option>
                            </select>
                            <input type="submit" class="btn btn-outline-secondary" value="Attempt Quiz Now"/>
                        </div>
                    </form>
                </div>
            </div>
            <hr>
        </div> <br/> <br/>

        <div class="row welcome text-center">
            <div class="col-12">
                <h3 class="display-6">Quiz History</h3>
            </div>
            <div id="search-quiz-form">
                <div class="col-12">
                    <form action="searchResult" method="POST">
                        <div class="form-inline">
                            <label>Choose subject: </label>
                            <select class="form-control" name="cbxSubject">
                                <option>All Subject</option>
                                <option>Prj311- Java Desktop</option>
                                <option>Prj321- Java Web</option>
                            </select>
                            <input type="submit" class="btn btn-outline-secondary" value="Search"/>
                        </div>
                    </form>
                </div>
            </div>
        </div> <br/>

        <c:set var="history" value="${sessionScope.QUIZ_RESULT_HISTORY}"/>
        <c:if test="${not empty history}">
            <div id="center-history-form">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Subject</th>
                            <th>Submitted</th>
                            <th>Result</th>
                            <th>Mark</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="h" items="${history}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${h.getSubject()}</td>
                                <td><fmt:formatDate pattern='dd/MM/yyyy HH:mm:ss' value='${h.getDueDate()}'/></td>
                                <td>
                                    <c:if test="${h.getSubject() == 'Prj311- Java Desktop'}">
                                        ${h.getCorrect_answers()}/40
                                    </c:if>
                                    <c:if test="${h.getSubject() == 'Prj321- Java Web'}">
                                        ${h.getCorrect_answers()}/50
                                    </c:if>
                                </td>
                                <td>${h.getMark()}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </body>
</html>
