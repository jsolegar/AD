<%-- 
    Document   : mostrarImagen
    Created on : 11-oct-2022, 20:09:26
    Author     : alumne
--%>

<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="Application.Image" contentType="text/html" pageEncoding="UTF-8"%>
<%
    Boolean control = false;
    HttpSession sesion = request.getSession();
    String usuario = (String) sesion.getAttribute("user");
    if (sesion != null && usuario != null) {
        control = true;
    }
    if (!control) {
        response.sendRedirect("login.jsp");
        return;
    }
    String id = request.getParameter("id");

%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show an Image</title>
    </head>
    <body>
        <div class="w3-container w3-teal w3-center">
            <h1>Show an Image</h>
        </div>

        <%
            out.println("<br><br><div align='center'><img src='http://localhost:8080/Practica4_REST/resources/javaee8/getImage/" + id + "'width='1000' height='525'  class='w3-card-4'></td>");
        %>
    </body>
</html>