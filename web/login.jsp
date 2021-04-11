<%-- 
    Document   : login
    Created on : Jan 30, 2021, 1:35:59 PM
    Author     : quocl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style><%@include file="/css/login.css"%></style>
        <title>Login Page</title>
    </head>
    <body id="LoginForm">
        <div class="container">
            <div class="login-form">
                <div class="main-div">
                    <div class="panel">
                        <h1>Login</h1>
                    </div> <br/>
                    <form action="login" id="Login" method="POST">
                        <div class="form-group">
                            <input type="email" class="form-control" id="inputText" placeholder="Email" name="txtEmail" value="" pattern="[a-z0-9._-]+@[a-z0-9.]+\.[a-z]{2,}$">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" id="inputPassword" placeholder="Password" name="txtPassword" value="">
                        </div>
                        <c:if test="${not empty requestScope.INVALID_ACCOUNT}">
                            <div class="form-group">
                                <font color="red"><c:out value="${requestScope.INVALID_ACCOUNT}"/></font>
                            </div>
                        </c:if>
                        <input type="submit" class="btn btn-primary" value="Login"/>
                    </form>
                    <br/>
                    <div class="text-center">
                        Don't have an account? <a href="sign_up" >Sign up</a>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>
