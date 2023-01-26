/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Application.Image;
import Driver.DriverImagen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
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
                String id = request.getParameter("id");
                Boolean combinada1 = false;
                Boolean combinada2 = false;
                if (title.isEmpty() && description.isEmpty() && key.isEmpty() && author.isEmpty() && captureDate.isEmpty() && id.isEmpty()) {
                   request.setAttribute("error", "No se ha introducido ningun campo de búsqueda");
                   RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
                   rd.forward(request,response);
                }
                else {
                    if (id.isEmpty()) {
                        if (!title.isEmpty()) combinada1 = true;
                 
                        if (!description.isEmpty()) {
                            combinada2 = true;
                        }    
                        if (!combinada2 && !key.isEmpty()) {
                            if (combinada1) combinada2 = true;
                            else  combinada1 = true;
                        }    
                        if (!combinada2 && !author.isEmpty()) {
                            if (combinada1) combinada2 = true;
                            else combinada1 = true;
                        }
                        if (!combinada2 && !captureDate.isEmpty()) {
                            if (combinada1) combinada2 = true;
                            else combinada1 = true;

                        }
                    }
                    //Si se filtra por más de un campo de búsqueda utilizamos la operación combinada
                    if(combinada2) {
                        List<Image> imagenes;
                        URL url = new URL("http://localhost:8080/Practica3_REST/resources/javaee8/combinedSearch");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setDoOutput(true);
                       /* Enviar los datos del formulario dentro de la petición */    
                       //"Hoy es " + variable + " aquí en Madrid."
                        String data = "title=" + title + "&description=" +description+ "&keywords=" +key+ "&author=" +author+"&captureDate=" +captureDate;
                        OutputStream os = conn.getOutputStream();
                        os.write(data.getBytes("utf-8"));

                     /* Recoger el resultado */
                        InputStreamReader in = new InputStreamReader(conn.getInputStream());
                        BufferedReader br = new BufferedReader(in);
                       
                        String result = String.valueOf(br.readLine());    
                        Gson gson = new Gson();
                        Type tipoListaImagenes = new TypeToken<List<Image>>(){}.getType();
                        imagenes = gson.fromJson(result, tipoListaImagenes);
                            conn.disconnect();
                        if (imagenes.isEmpty()) {
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
                             ListIterator<Image> iterator = imagenes.listIterator();
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
                            //    out.println("<td><a href='mostrarImagen.jsp?id="+image.id+"'><img src='Files/"+image.filename+"'width='100' height='75'class='w3-card-4'></td>");
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
                    //CASO DE QUE SOLO HAYA UN CAMPO DE FILTRADO DE BÚSQUEDA.
                    else {
                         List<Image> imagenes;
                        if (!id.isEmpty()) { //Si se busca por ID no tiene sentido combinar
                            URL url = new URL("http://localhost:8080/Practica3_REST/resources/javaee8/searchID/"+id);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            InputStreamReader in = new InputStreamReader(conn.getInputStream());
                            BufferedReader br = new BufferedReader(in);
                            String result = String.valueOf(br.readLine());
                            Gson gson = new Gson();
                            Image image = gson.fromJson(result, Image.class);
                            conn.disconnect();
                            if (image != null) { 
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
                                    "<th>Action</th>"+
                                    "<th></th>"+
                                "</tr>");
                            
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
                              //  out.println("<td><a href='mostrarImagen.jsp?id="+image.id+"'><img src='Files/"+image.filename+"'width='100' height='75'class='w3-card-4'></td>");
                                if (usuario.equals(image.creator)) {
                                    out.println("<td><a href='modificarImagen.jsp?id="+image.id+"'>Modify</a> or <a href='eliminarImagen.jsp?id="+image.id+"'>Delete</a></td>");
                                }    
                            out.println("</tr>");
                            
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
                            else {
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
                            
                        }
                        else if (!title.isEmpty()) {
                            URL url = new URL("http://localhost:8080/Practica3_REST/resources/javaee8/searchTitle/"+title);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            InputStreamReader in = new InputStreamReader(conn.getInputStream());
                            BufferedReader br = new BufferedReader(in);
                            String result = String.valueOf(br.readLine());
                            Gson gson = new Gson();
                            Type tipoListaImagenes = new TypeToken<List<Image>>(){}.getType();
                            imagenes = gson.fromJson(result, tipoListaImagenes);
                            conn.disconnect();
                           if (!imagenes.isEmpty()) {
                                 ListIterator<Image> iterator = imagenes.listIterator();
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
                               // out.println("<td><a href='mostrarImagen.jsp?id="+image.id+"'><img src='Files/"+image.filename+"'width='100' height='75'class='w3-card-4'></td>");
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
                            else {
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
                        }
                        else if (!key.isEmpty()) {
                            URL url = new URL("http://localhost:8080/Practica3_REST/resources/javaee8/searchKeywords/"+key);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            InputStreamReader in = new InputStreamReader(conn.getInputStream());
                            BufferedReader br = new BufferedReader(in);
                            String result = String.valueOf(br.readLine());
                            Gson gson = new Gson();
                            Type tipoListaImagenes = new TypeToken<List<Image>>(){}.getType();
                            imagenes = gson.fromJson(result, tipoListaImagenes);
                            conn.disconnect();
                           if (!imagenes.isEmpty()) {
                                 ListIterator<Image> iterator = imagenes.listIterator();
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
                           //     out.println("<td><a href='mostrarImagen.jsp?id="+image.id+"'><img src='Files/"+image.filename+"'width='100' height='75'class='w3-card-4'></td>");
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
                            else {
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
                        }
                        else if (!author.isEmpty()) {
                            URL url = new URL("http://localhost:8080/Practica3_REST/resources/javaee8/searchAuthor/"+author);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            InputStreamReader in = new InputStreamReader(conn.getInputStream());
                            BufferedReader br = new BufferedReader(in);
                            String result = String.valueOf(br.readLine());
                            Gson gson = new Gson();
                            Type tipoListaImagenes = new TypeToken<List<Image>>(){}.getType();
                            imagenes = gson.fromJson(result, tipoListaImagenes);
                            conn.disconnect();
                           if (!imagenes.isEmpty()) {
                                 ListIterator<Image> iterator = imagenes.listIterator();
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
                             //   out.println("<td><a href='mostrarImagen.jsp?id="+image.id+"'><img src='Files/"+image.filename+"'width='100' height='75'class='w3-card-4'></td>");
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
                            else {
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
                        }
                        else if (!captureDate.isEmpty()) {
                            URL url = new URL("http://localhost:8080/Practica3_REST/resources/javaee8/searchCreationDate/"+captureDate);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            InputStreamReader in = new InputStreamReader(conn.getInputStream());
                            BufferedReader br = new BufferedReader(in);
                            String result = String.valueOf(br.readLine());
                            Gson gson = new Gson();
                            Type tipoListaImagenes = new TypeToken<List<Image>>(){}.getType();
                            imagenes = gson.fromJson(result, tipoListaImagenes);
                            conn.disconnect();
                           if (!imagenes.isEmpty()) {
                                 ListIterator<Image> iterator = imagenes.listIterator();
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
                              //  out.println("<td><a href='mostrarImagen.jsp?id="+image.id+"'><img src='Files/"+image.filename+"'width='100' height='75'class='w3-card-4'></td>");
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
                            else {
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
                        }
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
