<%-- 
    Document   : list
    Created on : 08-oct-2022, 12:40:05
    Author     : alumne
--%>
<%@page import="com.google.gson.reflect.TypeToken"%>
<%@page import="java.lang.reflect.Type"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.ListIterator"%>
<%@page import="java.util.List"%>
<%@page import="Application.Image" contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>List of Images</title>
    </head>
    <body>
        <!-- Sidebar -->
        <div class="w3-sidebar w3-bar-block w3-border-right" style="display:none" id="mySidebar">
          <button onclick="w3_close()" class="w3-bar-item w3-large">Close &times;</button>
          <a href="registrarImagen.jsp" class="w3-bar-item w3-button">Register an Image</a>
          <a href="buscarImagen.jsp" class="w3-bar-item w3-button">Search an Image</a>
          <a href="menu.jsp" class="w3-bar-item w3-button">Menu</a>
          <a href="logout.jsp" class="w3-bar-item w3-button">Logout</a>
        </div>

        <div class="w3-teal">
          <button class="w3-button w3-teal w3-xlarge" onclick="w3_open()">☰</button>
          <div class="w3-container w3-center">
            <h1>List Images</h1>
          </div>
        </div>

        
        <table class="w3-table-all w3-card-4 w3-centered w3-hoverable w3-border">
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Keywords</th>
                <th>Author</th>
                <th>Creator</th>
                <th>Storage Data</th>
                <th>Capture Data</th>
                <th>Filename</th>
                <th>Action</th>
                <th></th>
            </tr>
        <%
            URL url = new URL("http://localhost:8080/Practica4_REST/resources/javaee8/list");
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("GET");
          
                
             InputStreamReader in = new InputStreamReader(conn.getInputStream());
             BufferedReader br = new BufferedReader(in);
             String result = String.valueOf(br.readLine());
             //l Empleado empleado = gson.fromJson(json, Empleado.class);
             Gson gson = new Gson();
             Type tipoListaImagenes = new TypeToken<List<Image>>(){}.getType();
             List<Image> imagenes = gson.fromJson(result, tipoListaImagenes);
             conn.disconnect();
            
 
            ListIterator<Image> iterator = imagenes.listIterator();
            while(iterator.hasNext()) {
                Image image = iterator.next();
                out.println("<tr>");
                out.println("<td>"+image.id+"</td>");
                out.println("<td>"+image.title+"</td>");
                out.println("<td>"+image.descrption+"</td>");
                out.println("<td>"+image.key_words+"</td>");
                out.println("<td>"+image.author+"</td>");
                out.println("<td>"+image.creator+"</td>");
                out.println("<td>"+image.storage_date+"</td>");
                out.println("<td>"+image.capture_date+"</td>");
                out.println("<td>"+image.filename+"</td>");
                out.println("<td><a href='mostrarImagen.jsp?id="+image.id+"'><img src='http://localhost:8080/Practica4_REST/resources/javaee8/getImage/"+image.id+"'width='100' height='75'class='w3-card-4'></td>");
                if (usuario != null && usuario.equals(image.creator)) {
                    out.println("<td><a href='modificarImagen.jsp?id="+image.id+"'>Modify</a> or <a href='eliminarImagen.jsp?id="+image.id+"'>Delete</a></td>");
                }    
                out.println("</tr>");
            }
            
        %>
        
        <script>
            function w3_open() {
              document.getElementById("mySidebar").style.display = "block";
            }
            function w3_close() {
              document.getElementById("mySidebar").style.display = "none";
            }
        </script>
        </table>
    </body>
</html>