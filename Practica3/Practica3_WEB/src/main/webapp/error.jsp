<%-- 
    Document   : error
    Created on : 04-oct-2022, 19:42:42
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String error = (String) request.getAttribute("error");
  
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
         <%
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");        
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=w3-teal>" +
            "<h1>Error Page</h1>"+
            "</div>");
           //Errores relacionados con el login
            if (error.equals("Error en el usuario o la contraseña")) {
                out.println("Incorrect User or Password");
                out.println("<div align='center'><a href='login.jsp'>Return to login</a></div>");
            }
            else if (error.equals("Faltan campos para rellenar o no se ha seleccionado imagen")) {
                out.println("Not all fields have been entered or an image has not been selected correctly");
                out.println("<div align='center'><a href='menu.jsp'>Return to menu</a></div>");
            }
            else if (error.equals("El contenido registrado no es el esperado")) {
                out.println("The registered content is not what was expected");
                out.println("<div align='center'><a href='menu.jsp'>Return to menu</a></div>");
            }
            else if (error.equals("Usuario no es creador")) {
                out.println("The user is not the owner of the image");
                out.println("<div align='center'><a href='menu.jsp'>Return to menu</a></div>");
            }
            else if (error.equals("Sesión no iniciada")) {
                out.println("This is not your session");
                out.println("<div align='center'><a href='menu.jsp'>Return to menu</a></div>");
            }
            else if (error.equals("No puedes modificar")) {
                out.println("No eres el Creador de esta imágen");
                out.println("<div align='center'><a href='menu.jsp'>Return to menu</a></div>");
            }
            else if (error.equals("Error al eliminar imagen de disco")) {
                out.println("Error deleting disk image");
                out.println("<div align='center'><a href='menu.jsp'>Return to menu</a></div>");
            }
            else if (error.equals("Usuario existe")) {
                out.println("User exists");
                out.println("<div align='center'><a href='login.jsp'>Return to login</a></div>");
            }
            else if (error.equals("contraseñas no coinciden")) {
                out.println("Passwords do not match");
                out.println("<div align='center'><a href='login.jsp'>Return to login</a></div>");
            }
            else if (error.equals("No se ha introducido ningun campo de búsqueda")) {
                out.println("No search field entered");
                out.println("<div align='center'><a href='menu.jsp'>Return to menu</a></div>");
                out.println("<div align='center'><a href='buscarImagen.jsp'>Return to search an image</a></div>");  
            }
            else {
                out.println("Unexpected error has ocurred");   
                out.println("<div align='center'><a href='menu.jsp'>Return to menu</a></div>");
            }

          %>
              
    </body>
</html>


