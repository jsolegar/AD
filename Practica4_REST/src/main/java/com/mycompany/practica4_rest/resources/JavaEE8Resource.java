package com.mycompany.practica4_rest.resources;

import Application.Image;
import Application.User;
import Driver.DriverAlmacenamiento;
import Driver.DriverImagen;
import Driver.DriverUser;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author
 */
@Path("javaee8")
public class JavaEE8Resource {

    /**
     * OPERACIONES DEL SERVICIO REST
     */
    /**
     * POST method to login in the application
     *
     * @param username
     * @param password
     * @return
     */
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Login(@FormParam("username") String username,
            @FormParam("password") String password) {
        if (!(username == null && password == null)) {
            DriverUser conex = new DriverUser();
            Boolean correct;
            correct = conex.CheckUserPass(username, password);
            if (correct) {
                return Response.ok(200, MediaType.APPLICATION_JSON).build();
            }
        }

        return Response.ok(404, MediaType.APPLICATION_JSON).build();

    }

    /**
     * POST method to register an user
     *
     * @param username
     * @param password
     * @param passwordConfirm
     * @return
     */
    @Path("registerUser")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response RegisterUser(@FormParam("username") String username,
            @FormParam("password") String password, @FormParam("passwordConfirm") String passwordConfirm) {
        if (!(username.equals(null) && password.equals(null) && passwordConfirm.equals(null))) {
            DriverUser conex = new DriverUser();
            if (!conex.CheckUser(username)) {
                if (password.equals(passwordConfirm)) {
                    User usuario = new User();
                    usuario.user = username;
                    usuario.password = password;
                    conex.NewUser(usuario);
                    return Response.ok(200, MediaType.APPLICATION_JSON).build();
                } else {
                    return Response.ok(400, MediaType.APPLICATION_JSON).build();
                }
            } else {
                return Response.ok(409, MediaType.APPLICATION_JSON).build();
            }
        }
        return Response.ok(404, MediaType.APPLICATION_JSON).build();
    }

    /**
     * POST method to register a new image
     *
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param creator
     * @param capt_date
     * @param filename
     * @param fileInputStream
     * @param fileMetaData
     * @return
     */
    @Path("register")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerImage(@FormDataParam("title") String title,
            @FormDataParam("description") String description,
            @FormDataParam("keywords") String keywords,
            @FormDataParam("author") String author,
            @FormDataParam("creator") String creator,
            @FormDataParam("capture") String capt_date,
            @FormDataParam("filename") String filename,
            @FormDataParam("file") InputStream fileInputStream) {

        if (title.isEmpty() || description.isEmpty() || keywords.isEmpty() || author.isEmpty() || capt_date.isEmpty() || filename.isEmpty()) {
            return Response.ok("400", MediaType.APPLICATION_JSON).build();
        } else {
            DriverAlmacenamiento a = new DriverAlmacenamiento();
            if (a.isExtension(filename, DriverAlmacenamiento.extens)) {
                Date date = new Date();
                DateFormat dateStorage = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    String photo = a.saveFile(fileInputStream, filename);
                } catch (Exception e) {
                    return Response.ok(405, MediaType.APPLICATION_JSON).build();
                }
                Image imagen = new Image("Da igual", title, description, keywords, author, creator, capt_date, dateStorage.format(date), filename);
                DriverImagen drI = new DriverImagen();
                drI.insertarImagen(imagen);
                return Response.ok(200, MediaType.APPLICATION_JSON).build();

            } else {
                return Response.ok("404", MediaType.APPLICATION_JSON).build();
            }
        }
    }

    /**
     * POST method to modify an existing image
     *
     * @param id
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param creator, used for checking image ownership
     * @param capt_date
     * @param filename
     * @param filenameOld
     * @param filenameOld2
     * @param fileInputStream
     * @return
     */
    @Path("modify")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyImage(@FormDataParam("id") String id,
            @FormDataParam("title") String title,
            @FormDataParam("description") String description,
            @FormDataParam("keywords") String keywords,
            @FormDataParam("author") String author,
            @FormDataParam("creator") String creator,
            @FormDataParam("capture") String capt_date,
            @FormDataParam("filename") String filename,
            @FormDataParam("filenameOld") String filenameOld,
            @FormDataParam("filenameOld2") String filenameOld2,
            @FormDataParam("file") InputStream fileInputStream) {

        if (title.isEmpty() || description.isEmpty() || keywords.isEmpty() || author.isEmpty() || capt_date.isEmpty() || filename.isEmpty()) {
            return Response.ok(404, MediaType.APPLICATION_JSON).build();
        } else {
            DriverAlmacenamiento a = new DriverAlmacenamiento();
            try {
                if (filenameOld.equals("old")) { //Se ha subido un archivo diferente
                    if (a.isExtension(filename, DriverAlmacenamiento.extens)) {
                        String photo = a.saveFile(fileInputStream, filename);                      
                        if (!a.deleteFile(filenameOld2)) {
                            return Response.ok(405, MediaType.APPLICATION_JSON).build();
                        }
                    }
                }
            } catch (IOException e) {
                return Response.ok(405, MediaType.APPLICATION_JSON).build();
            }
            DriverImagen drI = new DriverImagen();
            Image img = drI.searchImageID(id);
            if (creator != null && img.creator.equals(creator)) {
                if (a.isExtension(filename, DriverAlmacenamiento.extens)) {
                    Date date = new Date();
                    DateFormat dateStorage = new SimpleDateFormat("dd-MM-yyyy");
                    Image imagen = new Image(id, title, description, keywords, author, creator, capt_date, dateStorage.format(date), filename);
                    drI.modificarImagen(imagen);
                    return Response.ok(200, MediaType.APPLICATION_JSON).build();
                }
            } else {
                return Response.ok(403, MediaType.APPLICATION_JSON).build();
            }
        }
        return Response.ok(404, MediaType.APPLICATION_JSON).build();
    }

    /**
     * POST method to delete and existing image
     *
     * @param id
     * @param creator, used for checking image ownership
     * @return
     */
    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteImage(@FormParam("id") String id,
            @FormParam("creator") String creator) {
        DriverImagen drI = new DriverImagen();
        Image img = drI.searchImageID(id);
        DriverAlmacenamiento a = new DriverAlmacenamiento();
        if (creator != null && img.creator.equals(creator)) {
             try {
                 if(a.deleteFile(img.filename)) {
                     drI.eliminarImagen(id);
                     return Response.ok(200, MediaType.APPLICATION_JSON).build();
                 }
                 else {
                     return Response.ok(405, MediaType.APPLICATION_JSON).build();
                 }
             }
            catch (IOException e) {
                return Response.ok(405, MediaType.APPLICATION_JSON).build();
            }
        }
        return Response.ok(403, MediaType.APPLICATION_JSON).build();
        
    }

    /**
     * GET method to list images
     *
     * @return
     *
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listImages() {
        DriverImagen DI = new DriverImagen();
        List<Image> images = DI.ListImagesDB();
        Gson gson = new Gson();
        String representacionJSON = gson.toJson(images);
        return Response.ok(200, MediaType.APPLICATION_JSON).entity(representacionJSON).build();
    }

    /**
     * GET method to search images by id
     *
     * @param id
     * @return
     */
    @Path("searchID/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByID(@PathParam("id") int id) {
        DriverImagen drI = new DriverImagen();
        String ID = Integer.toString(id);
        Image img = drI.searchImageID(ID);
        Gson gson = new Gson();
        String representacionJSON = gson.toJson(img);

        return Response.ok(200, MediaType.APPLICATION_JSON).entity(representacionJSON).build();
    }

    /**
     * GET method to search images by title
     *
     * @param title
     * @return
     */
    @Path("searchTitle/{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByID(@PathParam("title") String title) {
        DriverImagen DI = new DriverImagen();
        List<Image> images = DI.searchImageTitle(title);
        Gson gson = new Gson();
        String representacionJSON = gson.toJson(images);
        return Response.ok(200, MediaType.APPLICATION_JSON).entity(representacionJSON).build();
    }

    /**
     * GET method to search images by creation date. Date format should be
     * yyyy-mm-dd
     *
     * @param date
     * @return
     */
    @Path("searchCreationDate/{date}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByCreationDate(@PathParam("date") String date) {
        DriverImagen DI = new DriverImagen();
        List<Image> images = DI.searchImagebyCreationDate(date);
        Gson gson = new Gson();
        String representacionJSON = gson.toJson(images);
        return Response.ok(200, MediaType.APPLICATION_JSON).entity(representacionJSON).build();
    }

    /**
     * GET method to search images by author
     *
     * @param author
     * @return
     */
    @Path("searchAuthor/{author}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByAuthor(@PathParam("author") String author) {
        DriverImagen DI = new DriverImagen();
        List<Image> images = DI.searchImageAuthor(author);
        Gson gson = new Gson();
        String representacionJSON = gson.toJson(images);
        return Response.ok(200, MediaType.APPLICATION_JSON).entity(representacionJSON).build();
    }

    /**
     * GET method to search images by keyword
     *
     * @param keywords
     * @return
     */
    @Path("searchKeywords/{keywords}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByKeywords(@PathParam("keywords") String keywords) {
        DriverImagen DI = new DriverImagen();
        List<Image> images = DI.searchImageKeywords(keywords);
        Gson gson = new Gson();
        String representacionJSON = gson.toJson(images);
        return Response.ok(200, MediaType.APPLICATION_JSON).entity(representacionJSON).build();
    }

    /**
     * GET methor to search images by title,description,keywords,author,creation
     * date
     *
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param captureDate
     * @return
     */
    @Path("combinedSearch")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)

    public Response combinedSearch(@FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("keywords") String keywords,
            @FormParam("author") String author,
            @FormParam("captureDate") String captureDate) {
        DriverImagen DI = new DriverImagen();
        List<Image> images = DI.BusquedaImagen(title, description, keywords, author, captureDate);
        Gson gson = new Gson();
        String representacionJSON = gson.toJson(images);
        return Response.ok(200, MediaType.APPLICATION_JSON).entity(representacionJSON).build();
    }
    /**
     * GET method to download image
     * @param id
     * @return 
     */
    @Path("getImage/{id}")
    @GET
    @Produces("image/*")
    public Response getImage(@PathParam("id") String id) {
        try {
            DriverImagen drI = new DriverImagen();
            Image img = drI.searchImageID(id);
            File f = new File("/home/alumne/AD/AD/Practica4/Practica4_REST/Files/"+img.filename);
            if (!f.exists()) return Response.ok(403, MediaType.APPLICATION_JSON).build();           
            String mt = new MimetypesFileTypeMap().getContentType(f);
            String filename = img.filename;                         
            return Response.ok(f, mt).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename).build();
        } catch (Exception e) {
            return Response.ok(405, MediaType.APPLICATION_JSON).build();
        }
    }

}
