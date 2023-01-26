<%-- 
    Document   : register
    Created on : 12-oct-2022, 16:35:10
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <div class="w3-container w3-teal w3-center">
            <h1>Register</h1>
        </div>
        <div>
            <form class="w3-container" action='registrarUsuario' method="POST">  
                <label class="w3-text-teal"><p><b>User</b>
                <input class="w3-input w3-border w3-light-grey" type='text' placeholder="User" name="user"></p>
                <label class="w3-text-teal"><p><b>Password</b>
                <input class="w3-input w3-border w3-light-grey" type='password' placeholder="Password" name="password"> 
                <label class="w3-text-teal"><p><b>Confirm Password</b>
                <input class="w3-input w3-border w3-light-grey" type='password' placeholder="Password" name="password_confirm"> 
                <button class="w3-button w3-round-xlarge w3-teal w3-hover-blue w3-section" type='submit' name="submit">Submit</button>
            </form>
        </div>
    </body>
</html>
