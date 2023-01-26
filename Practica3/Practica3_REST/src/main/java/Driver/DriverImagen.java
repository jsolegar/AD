/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver;

import Application.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumne
 */
public class DriverImagen {
     
    public void insertarImagen(Image imagen) {
         Connection con = null;
        try {
            String query = "INSERT INTO IMAGE (TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME) VALUES (?,?,?,?,?,?,?,?)";
            DriverConexion dr = new DriverConexion();
            con = dr.DBConexion(con); //abrimos la conexion
            PreparedStatement statement;
            statement = con.prepareStatement(query);
            //statement.setString(1, imagen.id);
            statement.setString(1, imagen.title);
            statement.setString(2,imagen.descrption);
            statement.setString(3,imagen.key_words);
            statement.setString(4, imagen.author);
            statement.setString(5,imagen.creator);
            statement.setString(6,imagen.capture_date);
            statement.setString(7,imagen.storage_date);
            //System.out.println(imagen.filename);
            statement.setString(8,imagen.filename);
            statement.executeUpdate();
            
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally { //cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
         
     }
     
     public List<Image> ListImagesDB() {
         Connection con = null;
         List<Image> images = new ArrayList<>();
         try {
            String query = "SELECT * FROM image";
            DriverConexion dr = new DriverConexion();
            con = dr.DBConexion(con); //abrimos la conexion
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                Image img;
                img = new Image();
                img.id = rs.getString("id");
                img.title = rs.getString("title");
                img.descrption = rs.getString("description");
                img.key_words = rs.getString("keywords");
                img.author = rs.getString("author");
                img.creator = rs.getString("creator");
                img.capture_date = rs.getString("capture_date");
                img.storage_date = rs.getString("storage_date");
                img.filename = rs.getString("filename");
                
                images.add(img);
                
            }
         }
         catch (SQLException e) {
             System.err.println(e.getMessage());
         }
         finally { //cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }   
        return images;
     }
     
     public Image searchImageID (String id) {
        Connection con = null;
        Image img = null;
        try {
           String query = "SELECT * FROM image WHERE id = ?";
           con = new DriverConexion().DBConexion(con);
           PreparedStatement statement = con.prepareStatement(query);
           statement.setString(1, id);
           ResultSet rs = statement.executeQuery();
           if (rs.next()) {
               img = new Image(
                   rs.getString("id"),
                   rs.getString("title"),
                   rs.getString("description"),
                   rs.getString("keywords"),
                   rs.getString("author"),
                   rs.getString("creator"),
                   rs.getString("capture_date"),
                   rs.getString("storage_date"),
                   rs.getString("filename")                                 
               );           
           } 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally { //cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
               System.err.println(e.getMessage());
            }
        }   
        return img;
     }
     
     public List<Image> searchImageTitle (String title) {
        Connection con = null;
        List<Image> list =new ArrayList<>();
        try {
           String query = "SELECT * FROM image WHERE title like ?";
           con = new DriverConexion().DBConexion(con);
           PreparedStatement statement = con.prepareStatement(query);
           statement.setString(1, "%"+title+"%");
           ResultSet rs = statement.executeQuery();
           System.out.println("SI");
           while (rs.next()) {
               Image imagen = new Image();
               imagen.id = rs.getString("id");
               System.out.println(rs.getString("title"));
               System.out.println("SI");
               imagen.title = rs.getString("title");
               imagen.descrption= rs.getString("description");
               imagen.key_words = rs.getString("keywords");
               imagen.author =rs.getString("author");
               imagen.creator = rs.getString("creator");
               imagen.capture_date = rs.getString("capture_date");
               imagen.storage_date = rs.getString("storage_date");
               imagen.filename = rs.getString("filename");
               list.add(imagen);          
           } 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally { //cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
               System.err.println(e.getMessage());
            }
        }   
        return list;
     }
     
     public List<Image> searchImagebyCreationDate (String capture_date) {
        Connection con = null;
        List<Image> list =new ArrayList<>();
        try {
           String query = "SELECT * FROM image WHERE capture_date like ?";
           con = new DriverConexion().DBConexion(con);
           PreparedStatement statement = con.prepareStatement(query);
           statement.setString(1, "%"+capture_date+"%");
           ResultSet rs = statement.executeQuery();
           while (rs.next()) {
               Image imagen = new Image();
               imagen.id = rs.getString("id");
               imagen.title = rs.getString("title");
               imagen.descrption= rs.getString("description");
               imagen.key_words = rs.getString("keywords");
               imagen.author =rs.getString("author");
               imagen.creator = rs.getString("creator");
               imagen.capture_date = rs.getString("capture_date");
               imagen.storage_date = rs.getString("storage_date");
               imagen.filename = rs.getString("filename");
               list.add(imagen);          
           } 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally { //cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
               System.err.println(e.getMessage());
            }
        }   
        return list;
     }
     
     public List<Image> searchImageAuthor (String author) {
        Connection con = null;
        List<Image> list =new ArrayList<>();
        try {
           String query = "SELECT * FROM image WHERE author like ?";
           con = new DriverConexion().DBConexion(con);
           PreparedStatement statement = con.prepareStatement(query);
           statement.setString(1, "%"+author+"%");
           ResultSet rs = statement.executeQuery();
           while (rs.next()) {
               Image imagen = new Image();
               imagen.id = rs.getString("id");
               imagen.title = rs.getString("title");
               imagen.descrption= rs.getString("description");
               imagen.key_words = rs.getString("keywords");
               imagen.author =rs.getString("author");
               imagen.creator = rs.getString("creator");
               imagen.capture_date = rs.getString("capture_date");
               imagen.storage_date = rs.getString("storage_date");
               imagen.filename = rs.getString("filename");
               list.add(imagen);          
           } 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally { //cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
               System.err.println(e.getMessage());
            }
        }   
        return list;
     }
     
     public List<Image> searchImageKeywords (String keywords) {
        Connection con = null;
        List<Image> list =new ArrayList<>();
        try {
           String query = "SELECT * FROM image WHERE keywords like ?";
           con = new DriverConexion().DBConexion(con);
           PreparedStatement statement = con.prepareStatement(query);
           statement.setString(1, "%"+keywords+"%");
           ResultSet rs = statement.executeQuery();
           while (rs.next()) {
               Image imagen = new Image();
               imagen.id = rs.getString("id");
               imagen.title = rs.getString("title");
               imagen.descrption= rs.getString("description");
               imagen.key_words = rs.getString("keywords");
               imagen.author =rs.getString("author");
               imagen.creator = rs.getString("creator");
               imagen.capture_date = rs.getString("capture_date");
               imagen.storage_date = rs.getString("storage_date");
               imagen.filename = rs.getString("filename");
               list.add(imagen);          
           } 
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally { //cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
               System.err.println(e.getMessage());
            }
        }   
        return list;
     }
     
    public void modificarImagen(Image imagen) {
        Connection con = null;
        try {
            String query = "UPDATE IMAGE SET TITLE = ?,DESCRIPTION = ?, KEYWORDS = ?, AUTHOR = ?, CREATOR = ?, CAPTURE_DATE = ?, STORAGE_DATE = ?, FILENAME = ? WHERE id = ?";
            DriverConexion dr = new DriverConexion();
            con = dr.DBConexion(con); //abrimos la conexion
            PreparedStatement statement;
            statement = con.prepareStatement(query);
            statement.setString(1, imagen.title);
            statement.setString(2,imagen.descrption);
            statement.setString(3,imagen.key_words);
            statement.setString(4, imagen.author);
            statement.setString(5,imagen.creator);
            statement.setString(6,imagen.capture_date);
            statement.setString(7,imagen.storage_date);
            statement.setString(8,imagen.filename);
            int id = Integer.parseInt(imagen.id);
            statement.setInt(9,id);
            statement.executeUpdate();
            
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally { //cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
         
    }
    
    public void eliminarImagen(String id) {
        Connection con = null;
        try {
            String query = "DELETE FROM image WHERE id = ?";
            DriverConexion dr = new DriverConexion();
            con = dr.DBConexion(con); //abrimos la conexion
            PreparedStatement statement;
            statement = con.prepareStatement(query);    
            statement.setInt(1, Integer.parseInt(id));
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());            
        }
        finally { //cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
      
         
    }
    
    public List<Image> BusquedaImagen(String title, String description, String key, String author, String captureDate) {
        Connection con = null;
        List<Image> list = new ArrayList<>();
        String query="SELECT * FROM IMAGE WHERE";
         try {
               Boolean control1,control2,control3,control4,control5;
               control1=control2=control3=control4=control5 = false;
               int values;
               values = 0;
               if (!title.isEmpty()) {
                   System.out.println("entra");
                   control1 = true;
                   query += " TITLE LIKE ?";
                   ++values;
               }
               if (!description.isEmpty()) {
                   control2 = true;
                   if (values == 0) query+= " DESCRIPTION LIKE ?";
                   else query+= "AND DESCRIPTION LIKE ?";
                   ++values;
               }
               if (!key.isEmpty()) {
                   control3 = true;
                   if (values == 0) query+= " KEYWORDS LIKE ?";
                   else query+= " AND KEYWORDS LIKE ?";
                   ++values;
               }
               if (!author.isEmpty()) {
                   control4 = true;
                   if (values == 0) query+= " AUTHOR LIKE ?";
                   else query+= " AND AUTHOR LIKE ?";
                   ++values;
               }
               if (!captureDate.isEmpty()) {
                   control5 = true;
                   if (values == 0) query+= " CAPTURE_DATE LIKE ?";
                   else query+= " AND CAPTURE_DATE LIKE ?";
               }
               
              PreparedStatement statement;
              DriverConexion dr = new DriverConexion();
              con = dr.DBConexion(con); //abrimos conexion
              statement = con.prepareStatement(query);
              values = 0;
              //los % indican que buscan entre las imagenes la secuencia "title" en los titulos a buscar
               if (control1) {
                   ++values;
                   statement.setString(values, "%"+title+"%");
               }
               if (control2) {
                   ++values;
                   statement.setString(values,"%"+description+"%");
               }
               if(control3) {
                   ++values;
                   statement.setString(values,"%"+key+"%");
               }
               if(control4) {
                   ++values;
                   statement.setString(values,"%"+author+"%");
               }
               if(control5) {
                   ++values;
                   statement.setString(values,"%"+captureDate+"%");
               }
              
              ResultSet rs = statement.executeQuery();
              
              while(rs.next()) {
                  Image imagen = new Image();
                  imagen.id = rs.getString("id");
                  imagen.title = rs.getString("title");
                  imagen.descrption= rs.getString("description");
                  imagen.key_words = rs.getString("keywords");
                  imagen.author =rs.getString("author");
                  imagen.creator = rs.getString("creator");
                  imagen.capture_date = rs.getString("capture_date");
                  imagen.storage_date = rs.getString("storage_date");
                  imagen.filename = rs.getString("filename");
                  list.add(imagen); 
              }
         }
       catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally { //cerramos conexion
            try {
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
               System.err.println(e.getMessage());
            }
        }   
        return list;
     }
}
