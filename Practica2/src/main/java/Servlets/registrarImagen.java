/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Application.Image;
import Driver.DriverAlmacenamiento;
import Driver.DriverImagen;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author alumne
 */
@MultipartConfig
@WebServlet(name = "registrarImagen", urlPatterns = {"/registrarImagen"})
public class registrarImagen extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");        
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=w3-teal>" +
            "<h1>Register an Image</h1>"+
            "</div>");
            
            
            HttpSession sesion = request.getSession();
            String usuario = (String) sesion.getAttribute("user");
            if (sesion != null && usuario != null) {
                //obtenemos los cinco atributos que se piden en el formulario
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String key = request.getParameter("keywords");
                String author = request.getParameter("author");
                String captureDate = request.getParameter("capture_date");
                //Obtenemos la imagen que se quiere registrar 
                Part part = request.getPart("file");
              
                if (title.isEmpty() || description.isEmpty() || key.isEmpty() || author.isEmpty() || captureDate.isEmpty() || part == null) {
                    request.setAttribute("error", "Faltan campos para rellenar o no se ha seleccionado imagen");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                    dispatcher.forward(request,response);
                }
                else {
                   //DriverAlmacenamiento a = new DriverAlmacenamiento();
                   //if (a.isExtension(part.getSubmittedFileName(), DriverAlmacenamiento.extens)) {
                       Date date = new Date();
                       DateFormat dateFormat = new SimpleDateFormat("dd-MM.HH.mm.ss");                       
                       DateFormat dateStorage = new SimpleDateFormat("dd-MM-yyyy");
                       //String photo = a.saveFile(part, DriverAlmacenamiento.uploads, dateFormat.format(date));
                       String formato = dateFormat.format(date) + "-" + title;
                       Image imagen = new Image("Da igual",title,description,key,author,usuario, captureDate, dateStorage.format(date), formato);
                       DriverImagen drI = new DriverImagen();
                       drI.insertarImagen(imagen);
                       out.println("<div align='center'><a href=\"menu.jsp\">Return menu</a></div>");
                       out.println("<div align='center'><a href=\"registrarImagen.jsp\">Register an Image again</a></div>");
                   //}
                  /* else {
                    request.setAttribute("error", "El contenido registrado no es el esperado");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                    dispatcher.forward(request,response);
                   }*/
                }
            }
            else {
                request.setAttribute("error", "Sesión no iniciada");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                
            }
        }catch (Exception e) {
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
