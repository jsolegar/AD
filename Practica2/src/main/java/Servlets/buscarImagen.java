/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Application.Image;
import Driver.DriverImagen;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alumne
 */
@MultipartConfig
@WebServlet(name = "buscarImagen", urlPatterns = {"/buscarImagen"})
public class buscarImagen extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
        
            HttpSession sesion = request.getSession();
            String usuario = (String) sesion.getAttribute("user");
            if (sesion != null && usuario != null) {
                //obtenemos los cinco atributos que se piden en el formulario
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String key = request.getParameter("keywords");
                String author = request.getParameter("author");
                String captureDate = request.getParameter("capture_date");
                
            
              
                if (title.isEmpty() && description.isEmpty() && key.isEmpty() && author.isEmpty() && captureDate.isEmpty()) {
                   request.setAttribute("error", "No se ha introducido ningun campo de búsqueda");
                   RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
                   rd.forward(request,response);
                }
                else {
                    DriverImagen dr = new DriverImagen();
                    List<Image> list;
                    list = dr.BusquedaImagen(title, description, key, author, captureDate);  //obtenemos el resultado de la búsqueda.
                    if (list.isEmpty()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>");
                    out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");        
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div class=w3-teal>" +
                    "<h1>Search a User</h1>"+
                    "</div>");
                       out.println("No results found");
                       out.println("<div align='center'><a href='menu.jsp'>Return menu </a></div>");
                       out.println("<div align='center'><a href='buscarImagen.jsp'>Search again</a></div>"); 
                    }
                    else {
                        DriverImagen DI = new DriverImagen();
                        ListIterator<Image> iterator = list.listIterator();
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>");
                        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");        
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<div class='w3-sidebar w3-bar-block w3-border-right' style='display:none' id='mySidebar'>" + 
                         "<button onclick='w3_close()' class='w3-bar-item w3-large'>Close &times;</button>" + 
                         "<a href='registrarImagen.jsp' class='w3-bar-item w3-button'>Register an Image</a>"+
                         "<a href='buscarImagen.jsp' class='w3-bar-item w3-button'>Search an Image</a>"+
                        "<a href='menu.jsp' class='w3-bar-item w3-button'>Menu</a>" +
                         "<a href='logout.jsp' class='w3-bar-item w3-button'>Logout</a>");
                        out.println("</div>");

                        out.println("<div class=w3-teal>" +
                        "<button class=w3-button w3-teal w3-xlarge' onclick='w3_open()'>☰</button>" +
                        "<div class='w3-container w3-center'>" +
                        "<h1>Search Images</h1>"+
                        "</div>"+
                        "</div>");

        
                        out.println("<table class='w3-table-all w3-card-4 w3-centered w3-hoverable w3-border'>"+
                            "<tr>"+
                                "<th>ID</th>"+
                                "<th>Title</th>"+
                                "<th>Description</th>"+
                                "<th>Keywords</th>"+
                                "<th>Author</th>"+
                                "<th>Creator</th>"+
                                "<th>Storage Data</th>"+
                                "<th>Capture Data</th>"+
                                "<th>Filename</th>"+
                                "<th>Image</th>"+
                                "<th>Action</th>"+
                                "<th></th>"+
                            "</tr>");
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
                            out.println("<td><a href='mostrarImagen.jsp?id="+image.id+"'><img src='Files/"+image.filename+"'width='100' height='75'class='w3-card-4'></td>");
                            if (usuario.equals(image.creator)) {
                                out.println("<td><a href='modificarImagen.jsp?id="+image.id+"'>Modify</a> or <a href='eliminarImagen.jsp?id="+image.id+"'>Delete</a></td>");
                            }    
                        out.println("</tr>");
                        }
                        out.println("<script>"+
                                    "function w3_open() {"+
                                      "document.getElementById('mySidebar').style.display = 'block';"+
                                    "}"+

                                    "function w3_close() {"+
                                      "document.getElementById('mySidebar').style.display = 'none';"+
                                    "}"+
                                "</script>"+
                                "</table>"+
                            "</body>"+
                        "</html>");
                        
                    }
                }
            }  
        }
        
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
