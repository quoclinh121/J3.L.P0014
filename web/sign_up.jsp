<%-- 
    Document   : sign_up
    Created on : Jan 30, 2021, 3:49:13 PM
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
        <title>Sign Up Page</title>
    </head>
    <body id="LoginForm">
        <div class="container">
            <div class="login-form">
                <div class="main-div">
                    <div class="panel">
                        <h1>Create Account</h1>
                    </div> <br/>
                    <form action="createAccount" id="Login" method="POST">
                        <div class="form-group">
                            <input type="email" class="form-control" id="inputText" placeholder="Email" name="txtEmail" pattern="[a-z0-9._-]+@[a-z0-9.]+\.[a-z]{2,}$" required>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" id="inputPassword" placeholder="Password" name="txtPassword" required>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" id="inputText" placeholder="Full name" name="txtName" required>
                        </div>
                        
                        <input type="submit" class="btn btn-primary" value="Create"/>
                    </form>
                    <br/>
                    <div class="text-center">
                        <a href="" >I have an account</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
