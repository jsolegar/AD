/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Application.Image;
import Driver.DriverAlmacenamiento;
import Driver.DriverImagen;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
@WebServlet(name = "eliminarImagen", urlPatterns = {"/eliminarImagen"})
public class eliminarImagen extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");        
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=w3-teal>" +
            "<h1>Delete an Image</h1>"+
            "</div>");
            
            HttpSession sesion = request.getSession();
            String usuario = null;
            if (sesion != null && sesion.getAttribute("user") != null) {
                usuario = (String) sesion.getAttribute("user");
                String creator = (String) request.getParameter("creator");
                //System.out.println(usuario);
                //System.out.println(creator);
                 String id = request.getParameter("id");
                //Primero hay que eliminar de disco ya que sino después no se puede eliminar ya que el servicio REST la elimina de la base de datos
                // de forma que ya no se podria acceder a ella a través del filename.
                  if (creator != null && creator.equals(usuario)) {
                    DriverImagen drI = new DriverImagen();
                    Image img = drI.searchImageID(id);
                     if (!DriverAlmacenamiento.deleteFile(img.filename)) { //no elimina bien de disco
                    request.setAttribute("error", "Error al eliminar imagen de disco");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                }
                URL url = new URL("http://localhost:8080/Practica3_REST/resources/javaee8/delete");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);
               /* Enviar los datos del formulario dentro de la petición */    
                String data = "id=" +id+ "&creator=" +usuario;
                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes("utf-8"));

                 /* Recoger el resultado */
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(in);
                int result = Integer.valueOf(br.readLine());
                conn.disconnect();
               
                if (result == 200) {
                    out.println("The file has been deleted. ");
                    out.println("<div align='center'><a href=\"menu.jsp\">Return menu</a></div>");   
                }
                else {
                    request.setAttribute("error", "No puedes modificar");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
            else {
                request.setAttribute("error", "Sesión no iniciada");
                request.getRequestDispatcher("login.jsp").forward(request, response);
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
