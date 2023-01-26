<%-- 
    Document   : logout
    Created on : 08-oct-2022, 15:08:49
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logout</title>
    </head>
    <body>
        <%
            HttpSession sesion = request.getSession();
            if (!sesion.equals(null)) {
                sesion.invalidate();
                sesion = null;
            }
            response.sendRedirect("login.jsp");
        %>
    </body>
</html>
