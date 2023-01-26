<%-- 
    Document   : registrarImagen
    Created on : 05-oct-2022, 12:19:40
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Boolean control = false;
    HttpSession sesion = request.getSession();
    String usuario = (String) sesion.getAttribute("user");
    if (sesion != null && usuario != null) control = true;
    if (!control) response.sendRedirect("login.jsp");
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar an Imagen</title>
    </head>
    <body>
        <!-- Sidebar -->
        <div class="w3-sidebar w3-bar-block w3-border-right" style="display:none" id="mySidebar">
          <button onclick="w3_close()" class="w3-bar-item w3-large">Close &times;</button>
          <a href="list.jsp" class="w3-bar-item w3-button">List Images</a>
          <a href="buscarImagen.jsp" class="w3-bar-item w3-button">Search an Image</a>
          <a href="menu.jsp" class="w3-bar-item w3-button">Menu</a>
          <a href="logout.jsp" class="w3-bar-item w3-button">Logout</a>
        </div>
        <div class="w3-teal">
          <button class="w3-button w3-teal w3-xlarge" onclick="w3_open()">☰</button>
          <div class="w3-container w3-center">
            <h1>Register an Image</h1>
          </div>
        </div>
        <div>
            <form class="w3-container" action="registrarImagen" method="POST" enctype="multipart/form-data">  
                <label class="w3-text-teal"><p><b>Title</b>
                <input class="w3-input w3-border w3-light-grey" type="text" placeholder="Título" name="title">
                
                <label class="w3-text-teal"><p><b>Description</b>
                <input class="w3-input w3-border w3-light-grey" type="text" placeholder="Descripción" name="description"></p>
                
                <label class="w3-text-teal"><p><b>Keywords</b>
                <input class="w3-input w3-border w3-light-grey" type="text" placeholder="Palabras clave" name="keywords"></p>
                
                <label class="w3-text-teal"><p><b>Author</b>
                <input class="w3-input w3-border w3-light-grey" type="text" placeholder="Autor" name="author"></p>
                
                <label class="w3-text-teal"><p><b>Creation date</b>
                <input class="w3-input w3-border w3-light-grey" type="date" placeholder="Fecha de creación" name="capture_date"></p>
                
                <p><input type="file" name="file"></p>
                
                <p><button class="w3-button w3-round-xlarge w3-teal w3-hover-red" type='submit' name="submit">Submit</button></p>
            </form>
        <script>
            function w3_open() {
              document.getElementById("mySidebar").style.display = "block";
            }

            function w3_close() {
              document.getElementById("mySidebar").style.display = "none";
            }
        </script>
        </div>
    </body>
</html>


