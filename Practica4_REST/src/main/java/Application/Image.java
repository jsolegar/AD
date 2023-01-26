/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

/**
 *
 * @author alumne
 */
public class Image {
    public String id;
    public String title;
    public String descrption;
    public String key_words;
    public String author; //Persona que creó la imágen
    public String creator; //Usuario que inserta la imagen en el sistema
    public String capture_date;
    public String storage_date;
    public String filename;
    
    public Image() {
        
    }
    
    public Image(String id, String title, String description, String key_words, String author, String creator, String capture_date, String storage_date, String filename) {
        this.id = id;
        this.title = title;
        this.descrption = description;
        this.key_words = key_words;
        this.author = author;
        this.creator = creator;
        this.capture_date = capture_date;
        this.storage_date = storage_date;
        this.filename = filename;
    }
    
}
