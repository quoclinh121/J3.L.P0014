<%-- 
    Document   : update_page
    Created on : Feb 9, 2021, 2:25:30 PM
    Author     : quocl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style><%@include file="/css/update_page.css"%></style>
        <title>Update Page</title>
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

        <div id="back-button">
            <form action="searchQuestion" method="POST">
                <input type="submit" class="btn btn-outline-primary" value="<-- Go back to Search page" />
                <input type="hidden" name="page" value="${requestScope.CURRENT_PAGE}"/>
            </form>
        </div> <br/> <br/>

        <c:set var="question" value="${sessionScope.UPDATED_QUESTION}"/>

        <div class="row welcome text-center">
            <div class="col-12">
                <h2 class="display-6">Update Question</h2>
            </div>
        </div> <br/> <br/>
        <c:if test="${not empty requestScope.ERRORS}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert" style="margin: 0 auto; width: 70%; text-align: center;">
                ${requestScope.ERRORS}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <br/>
        </c:if>
        <div class="container-fluid-padding">
            <div id="update-form" >
                <div class="col-10">
                    <form action="updateQuestion" method="POST">
                        <div class="form-group row">
                            <label class="col-3 col-form-label text-right">Question Content: </label>
                            <div class="col-7">
                                <textarea type="text" class="form-control" rows="4" name="txtQuestionContent" required>${question.getQuestion_content()}</textarea>
                            </div>
                        </div>
                        <c:set var="answer" value="${fn:split(question.getAnswer_content(), '??')}"/>
                        <div class="form-group row">
                            <label class="col-3 col-form-label text-right">Answer A: </label>
                            <div class="col-7">
                                <textarea type="text" id="A" class="form-control" rows="3" name="txtAnswerA" required>${answer[0]}</textarea>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-3 col-form-label text-right">Answer B: </label>
                            <div class="col-7">
                                <textarea type="text" id="B" class="form-control" rows="3" name="txtAnswerB" required>${answer[1]}</textarea>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-3 col-form-label text-right">Answer C: </label>
                            <div class="col-7">
                                <textarea type="text" id="C" class="form-control" rows="3" name="txtAnswerC" required>${answer[2]}</textarea>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-3 col-form-label text-right">Answer D: </label>
                            <div class="col-7">
                                <textarea type="text" id="D" class="form-control" rows="3" name="txtAnswerD" required>${answer[3]}</textarea>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-3 col-form-label text-right">Correct Answer: </label>
                            <div class="col-3">
                                <select class="form-control" name="cbxCorrectAnswer">
                                    <option <c:if test="${question.getAnswer_correct() == answer[0]}">selected</c:if>>Answer A</option>
                                    <option <c:if test="${question.getAnswer_correct() == answer[1]}">selected</c:if>>Answer B</option>
                                    <option <c:if test="${question.getAnswer_correct() == answer[2]}">selected</c:if>>Answer C</option>
                                    <option <c:if test="${question.getAnswer_correct() == answer[3]}">selected</c:if>>Answer D</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label text-right">Create Date: </label>
                                <div class="col-3">
                                    <input type="text" class="form-control" value="<fmt:formatDate pattern='dd/MM/yyyy' value='${question.getCreateDate()}'/>" disabled/>
                            </div>
                        </div>  
                        <div class="form-group row">
                            <label class="col-3 col-form-label text-right">Subject: </label>
                            <div class="col-4">
                                <select class="form-control" name="cbxSubject">
                                    <option <c:if test="${question.getSubject() == 'Prj311- Java Desktop'}">selected</c:if>>Prj311- Java Desktop</option>
                                    <option <c:if test="${question.getSubject() == 'Prj321- Java Web'}">selected</c:if>>Prj321- Java Web</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-3 col-form-label text-right">Status: </label>
                                <div class="col-3">
                                    <select class="form-control" name="cbxStatus:">
                                        <option <c:if test="${question.getStatus() == 'Active'}">selected</c:if>>Active</option>
                                    <option <c:if test="${question.getStatus() == 'DeActive'}">selected</c:if>>DeActive</option>
                                    </select>
                                </div>
                            </div>
                            <div class="text-center">
                                <div class="col-10">
                                    <input type="submit" class="btn btn-lg btn-outline-success" value="Update" />
                                    <input type="hidden" name="page" value="${requestScope.CURRENT_PAGE}" />
                                <input type="hidden" name="questionID" value="${question.getQuestion_id()}" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
