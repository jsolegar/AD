/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver;

import Application.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.Part;

/**
 *
 * @author alumne
 */

public class DriverAlmacenamiento {
    private Image imagen = new Image();
    //public static final String pathFiles = "/home/alumne/Documentos/Laboratorio/AD/Practica3/Practica3_WEB/Files";
    public static final String pathFiles = "/home/alumne/AD/AD/Practica3/Practica3_WEB/Files";//PATH Jordi
    public static final File uploads = new File(pathFiles);
    public static final String[] extens = {".jpg", ".jpeg", ".png"};
    
    public DriverAlmacenamiento() {
        
    }
    
    public String saveFile(Part part, File pathUploads, String id){
        String pathAbsolute ="";
        try {
            Path path = Paths.get(part.getSubmittedFileName()); // obtenemos el nombre del archivo
            String filename = id + "-" + path.getFileName().toString(); //nombre de archivo
            InputStream input = part.getInputStream(); //archivo en si
            
            if (input != null) {
                File file = new File(pathUploads, filename); //enlazar ruta con nombre del archivo
                pathAbsolute = file.getAbsolutePath();
                Files.copy(input,file.toPath()); //guardamos en nuestro directorio
            }
        } catch (IOException e) {
            out.println(e.getStackTrace());
        }
        return pathAbsolute;
    }
    
    public boolean isExtension(String filename, String[] extensiones) {
        for (String et : extensiones) {
            if(filename.toLowerCase().endsWith(et))  return true;
            
        }
        return false;
    }
    
    public static Boolean deleteFile(String Filename) throws IOException{
            return Files.deleteIfExists(Paths.get(pathFiles +"/"+ Filename));  
    }
    
}

