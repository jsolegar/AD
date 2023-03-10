/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alumne
 */
@WebServlet(name = "registrarUsuario", urlPatterns = {"/registrarUsuario"})
public class registrarUsuario extends HttpServlet {

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
            "<h1>Register a User</h1>"+
            "</div>");

            String user = request.getParameter("user");
            String password = request.getParameter("password");
            String password_confirm = request.getParameter("password_confirm");
            
            URL url = new URL("http://localhost:8080/Practica4_REST/resources/javaee8/registerUser");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
           /* Enviar los datos del formulario dentro de la petici??n */    
            String data = "username=" + user + "&password=" +password+ "&passwordConfirm=" +password_confirm;
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes("utf-8"));
            
             /* Recoger el resultado */
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            int result = Integer.valueOf(br.readLine()); 
            conn.disconnect();
            
            switch (result) {
                case 200:
                    response.sendRedirect("login.jsp");
                    break;
                case 400:
                    {
                        request.setAttribute("error", "contrase??as no coinciden");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                        dispatcher.forward(request, response);
                        break;
                    }
                case 409:
                    {
                        request.setAttribute("error", "Usuario existe");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                        dispatcher.forward(request, response);
                        break;
                    }
                case 404:
                    {
                        request.setAttribute("error", "Error en el usuario o la contrase??a");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                        dispatcher.forward(request, response);
                        break;
                    }
                default:
                    break;
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
