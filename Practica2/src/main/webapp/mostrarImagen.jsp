<%-- 
    Document   : mostrarImagen
    Created on : 11-oct-2022, 20:09:26
    Author     : alumne
--%>

<%@page import="Driver.DriverImagen,Application.Image" contentType="text/html" pageEncoding="UTF-8"%>
<%
    Boolean control = false;
    HttpSession sesion = request.getSession();
    String usuario = (String) sesion.getAttribute("user");
    if (!sesion.equals(null) && !usuario.equals(null)) control = true;
    if (!control) response.sendRedirect("login.jsp");


    DriverImagen DI = new DriverImagen();
    Image image = DI.searchImageID(request.getParameter("id"));
    if (image.equals(null)) {
        response.sendRedirect("menu.jsp");
    }
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
            out.println("<br><br><div align='center'><img src='Files/"+image.filename+"'width='1000' height='525'  class='w3-card-4'></td>");
        %>
    </body>
</html>
