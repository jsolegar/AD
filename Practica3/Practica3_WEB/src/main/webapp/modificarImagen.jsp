<%-- 
    Document   : modificarimagen
    Created on : 09-oct-2022, 14:59:15
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Application.Image,Driver.DriverImagen"%>
<%  
    Image img = null;
        try {
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
            DriverImagen dr = new DriverImagen();
            
            if (id != null) {
                img = dr.searchImageID(id);
            }
            if (img == null) {
                request.setAttribute("error", "Usuario no es creador");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modify Image</title>
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
          <button class="w3-button w3-teal w3-xlarge" onclick="w3_open()">???</button>
          <div class="w3-container w3-center">
            <h1>Modify an Image</h1>
          </div>
        </div>
         
        <form action="modificarImagen" method = "POST" enctype="multipart/form-data">
            <table class="w3-responsive w3-card-4 w3-centered w3-hoverable">
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
                    out.println("<tr>");
                    out.println("<td><label>"+img.id+"</label></td>");
                    out.println("<input type='hidden' name='id' value='"+img.id+"'/>");
                    out.println("<td><input type='text' value='"+img.title+"' name='title'/></td>");
                    out.println("<td><input type='text' value='"+img.descrption+"' name='description'/></td>");
                    out.println("<td><input type='text' value='"+img.key_words+"' name='keywords'/></td>");
                    out.println("<td><input type='text' value='"+img.author+"' name='author'/></td>");
                    out.println("<td><label>"+img.creator+"</label></td>");
                    out.println("<input type='hidden' name='creator' value='"+img.creator+"'/>");
                    out.println("<td><label>"+img.storage_date+"</label></td>");
                    out.println("<input type='hidden' name='storage_date' value='"+img.storage_date+"'/>");
                    out.println("<td><input type='date' value='"+img.capture_date+"' name='capture_date'/></td>");
                  //  out.println("<td><a href='mostrarImagen.jsp?id="+img.id+"'><img src='Files/"+img.filename+"'width='100' height='75'class='w3-card-4'><br>"
                    //        + "<label>"+img.filename+"</label></td>");
                    out.println("<input type='hidden' name='filename' value='"+img.filename+"'/>");
                %>
                <td><input type='file' name='file'/></td>
                <td><button class="w3-button w3-round-xlarge w3-teal w3-hover-red" type='submit' name="submit">Submit</button>
            </table>
        </form> 
        <script>
            function w3_open() {
              document.getElementById("mySidebar").style.display = "block";
            }

            function w3_close() {
              document.getElementById("mySidebar").style.display = "none";
            }
        </script>
    </body>
</html>