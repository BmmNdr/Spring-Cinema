package com.example.cinema;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MyRestController {

    @GetMapping("/api/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) throws SQLException {

        JSONObject response = new JSONObject();

        // Check if the password is correct. If it is, the token is returned and added to the database
        String token = DatabaseController.getInstance().checkPassword(username, password);

        //Status is true if the token is not null.
        response.put("status", token != null);
        response.put("token", token);

        return response.toString();
    }

    @GetMapping("/api/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email) throws SQLException {

        JSONObject response = new JSONObject();

        // Check if the user already exists in the database
        Boolean userExists = DatabaseController.getInstance().checkUserExists(username, email);

        if (userExists)
            response.put("status", false);
        else {
            //if the users does not exist, save it to the database
            DatabaseController.getInstance().saveNewUser(username, password, email);
            response.put("status", true);
        }

        return response.toString();
    }

    // Get the list of films from the database only if the token is valid.
    //The filter parameter is % by default, so it will match all the films in the SELECT WHERE LIKE statement.
    @GetMapping("/api/films")
    public List<Film> films(@RequestParam(value="filter", required = false, defaultValue = "%") String filter, @RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {

        if(!DatabaseController.getInstance().checkToken(token, username))
            return null;
            
        List<Film> films = DatabaseController.getInstance().getFilms(filter);

        return films;
    }

    //Checks if the token is valid
    @GetMapping("/api/checkToken")
    public Boolean checkToken(@RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {
        return DatabaseController.getInstance().checkToken(token, username);
    }

    //Checks if the user is an admin
    @GetMapping("/api/checkAdmin")
    public Boolean checkAdmin(@RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {
        return DatabaseController.getInstance().checkAdmin(token, username);
    }

    //Gets a single film from the database by its id only if the token is valid.
    @GetMapping("/api/film")
    public Film film(@RequestParam(value="id", required = true) String id, @RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {

        if(!DatabaseController.getInstance().checkToken(token, username))
            return null;

        Film films = DatabaseController.getInstance().getFilmById(id);

        return films;
    }

    //Removes a film from the database only if user is an admin (the check token is included in the chekAdmin method)
    @GetMapping("/api/removeFilm")
    public void removeFilm(@RequestParam(value="id", required = true) String id, @RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {

        if(DatabaseController.getInstance().checkAdmin(token, username)){
            //Get the image path of the film
            String path = DatabaseController.getInstance().getFilmById(id).imagePath;

            //Remove the image from the server
            if(path != null){
                File file = new File("src\\main\\resources\\static\\images\\" + path);
                if (file.exists()) {
                    file.delete();
                }
            }

            DatabaseController.getInstance().removeFilm(id);
        }
    }

    //Adds a film to the database only if user is an admin
    @GetMapping("/api/addFilm")
    public void addFilm(@RequestParam(value="imagePath", required = true) String imagePath, @RequestParam(value="genre", required = true) String genre, @RequestParam(value="title", required = true) String title, @RequestParam(value="description", required = true) String description, @RequestParam(value="release_date", required=true) String release_date, @RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {

        if(DatabaseController.getInstance().checkAdmin(token, username)){
            DatabaseController.getInstance().addFilm(title, description, release_date, genre, imagePath);
        }
    }

    @PostMapping(value = "/api/uploadPoster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadPoster(@RequestParam("file") MultipartFile file) throws IOException {
        //Get the file name
        String fileName = file.getOriginalFilename();

        //Save the file to the server
        File newFile = new File("src\\main\\resources\\static\\images\\" + fileName);
        newFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(file.getBytes());
        fos.close();
    }
}