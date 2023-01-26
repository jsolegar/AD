<%-- 
    Document   : menu
    Created on : 04-oct-2022, 19:24:51
    Author     : alumne
--%>

<%
    Boolean control = false;
    HttpSession sesion = request.getSession();
    String usuario = (String) sesion.getAttribute("user");
    if (!sesion.equals(null) && usuario != null) control = true;
    if (!control) response.sendRedirect("login.jsp");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>
    </head>
    <body>
        <div class="w3-container w3-teal w3-center">
            <h1>Menu</h> 
        </div>
        <div>
            
        </div>
        <div class="w3-container w3-center">
            <br>
            <br>
            <div class="w3-panel w3-teal w3-card-4">
                <p><a href="registrarImagen.jsp">Register an Image</a></p>
            </div>
            <div class="w3-panel w3-blue-gray w3-card-4">
                <p><a href="list.jsp">List Images</a></p>
            </div>            
            <div class="w3-panel w3-teal w3-card-4">
                <p><a href="buscarImagen.jsp">Search Images</a></p>
            </div>
            <div class="w3-panel w3-blue-gray w3-card-4">
                <p><a href="logout.jsp">Logout</a></p>
            </div> 
        </div>
    </body>
</html>


   


