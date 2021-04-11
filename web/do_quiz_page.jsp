<%-- 
    Document   : do_quiz_page
    Created on : Feb 11, 2021, 3:18:29 PM
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
        <style><%@include file="/css/do_quiz_page.css"%></style>
        <title>Do Quiz Page</title>
        <script type="text/javascript">
            function countdownTimer() {
                var timeRemaining = document.getElementById('timeRemaining').value;
                var x = setInterval(function () {
                    --timeRemaining;
                    var hours = Math.floor(timeRemaining / 3600);
                    var minutes = Math.floor(timeRemaining % 3600 / 60);
                    var seconds = Math.floor(timeRemaining % 3600 % 60);

                    document.getElementById("timer").innerHTML = "Remain: " + hours + "h "
                            + minutes + "m " + seconds + "s ";

                    document.getElementById("timeRemaining").value = timeRemaining;

                    if (timeRemaining <= 0) {
                        clearInterval(x);
                        var redirect = "changeQuestion?btnSubmit=submit";
                        if(document.querySelector('input[name="answer"]:checked') !== null) {
                            var answer = document.querySelector('input[name="answer"]:checked').value;
                            redirect += "&answer=" + answer + "&currentQuestion=${requestScope.CURRENT_QUESTION}";
                            window.location.href = redirect;
                        }
                    }
                }, 1000);
            }
        </script>
    </head>
    <body onload="countdownTimer()">
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

        <c:if test="${requestScope.QUIZ_RESULT != null}">
            <c:set var="result" value="${requestScope.QUIZ_RESULT}"/>
            <div class="container">
                <div id="result-form">
                    <div class="row welcome text-center">
                        <div class="col-12">
                            <h2 class="display-6">Quiz Result</h2>
                        </div>
                        <div class="col-12">
                            <c:if test="${sessionScope.SUBJECT_QUIZ == 'Prj311- Java Desktop'}">
                                <b>Correct:</b><c:out value=" ${result.getCorrect_answers()}/40"/>
                            </c:if>
                            <c:if test="${sessionScope.SUBJECT_QUIZ == 'Prj321- Java Web'}">
                                <b>Correct:</b><c:out value=" ${result.getCorrect_answers()}/50"/>
                            </c:if>
                            <br/>
                            <b>Mark:</b><c:out value=" ${result.getMark()}"/>
                        </div> <br/>
                        <div class="col-12">
                            <form action="home" method="POST">
                                <input type="submit" class="btn btn-outline-secondary" value="Back To Home Page"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${requestScope.QUIZ_RESULT == null}">
            <div class="container-fluid padding">
                <div class="row welcome text-center">
                    <div class="col-12">
                        <c:if test="${sessionScope.SUBJECT_QUIZ == 'Prj311- Java Desktop'}">
                            <h2 class="display-6">Java Desktop Quiz</h2> 
                        </c:if>                        
                        <c:if test="${sessionScope.SUBJECT_QUIZ == 'Prj321- Java Web'}">
                            <h2 class="display-6">Java Web Quiz</h2> 
                        </c:if>                        
                    </div>
                </div>
            </div> <br/> <br/>

            <div class="text-center">
                <div class="col-12">
                    <h3><span class="border border-primary" id="timer"></span></h3>
                </div>
            </div>

            <c:set var="question" value="${sessionScope.QUESTION}"/>
            <c:set var="answerList" value="${sessionScope.STUDENT_ANSWER_LIST}"/>
            <c:set var="questionNumber" value="${requestScope.CURRENT_QUESTION}"/>

            <div class="container">
                <div class="d-flex justify-content-center row">
                    <div class="col-md-10 col-lg-10">
                        <div class="border" id="question-form">
                            <form action="changeQuestion" method="POST">
                                <div class="d-flex flex-row align-items-center question-title">
                                    <h3 class="text-danger">Q. ${questionNumber + 1}</h3>
                                    <h5 class="mt-1 ml-2">${question.getQuestion_content()}</h5>
                                </div>
                                <c:set var="answer_split" value="${fn:split(question.getAnswer_content(), 'Ñ')}"/>
                                <c:forEach var="a" items="${answer_split}">
                                    <div class="ml-3">
                                        <input type="radio" name="answer" value="${a}" <c:if test="${answerList.get(questionNumber) == a}">checked</c:if> /> <c:out value="${a}"/>
                                        </div>
                                </c:forEach>
                                <div class="float-right">
                                    <input type="hidden" id="timeRemaining" name="timeRemain" value="${sessionScope.TIME}" />
                                    <c:if test="${questionNumber == (answerList.size() - 1)}">
                                        <input type="hidden" name="questionNumber" value="0" />
                                    </c:if>
                                    <c:if test="${questionNumber != (answerList.size() - 1)}">
                                        <input type="hidden" name="questionNumber" value="${questionNumber + 1}" />
                                    </c:if>
                                    <input type="hidden" name="currentQuestion" value="${questionNumber}" />
                                    <input type="submit" id="button-next" class="btn btn-primary border-success" value="Next" />
                                </div> <br/>
                                <hr> <br/>
                                <div id="question-list">
                                    <nav>
                                        <ul class="pagination nav justify-content-center">
                                            <c:forEach var="i" begin="1" end="${answerList.size()}">
                                                <c:choose>
                                                    <c:when test="${(questionNumber + 1) == i}">
                                                        <input type="button" class="btn btn-outline-secondary" value="${i}" disabled style="margin: 5px"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="submit" class="btn btn-outline-secondary" value="${i}" name="chooseQuestionNumber" style="margin: 5px"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </ul>
                                    </nav>
                                </div>
                                <hr> <br/>
                                <div class="text-center">
                                    <div class="col-12">
                                        <input type="submit" id="button-submit" class="btn btn-outline-secondary" name="btnSubmit" value="Submit" onclick="return confirm('Do you want to submit?')" />
                                    </div>
                                </div>
                            </form> 
                        </div>
                    </div>
                </div>
            </c:if>
    </body>
</html>
