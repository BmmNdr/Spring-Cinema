package com.example.cinema;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

    @GetMapping("/api/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) throws SQLException {

        JSONObject response = new JSONObject();

        String token = DatabaseController.getInstance().checkPassword(username, password);

        response.put("status", token != null);
        response.put("token", token);

        return response.toString();
    }

    @GetMapping("/api/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email) throws SQLException {

        JSONObject response = new JSONObject();

        Boolean userExists = DatabaseController.getInstance().checkUserExists(username, email);

        if (userExists)
            response.put("status", false);
        else {
            DatabaseController.getInstance().saveNewUser(username, password, email);
            response.put("status", true);
        }

        return response.toString();
    }

    @GetMapping("/api/films")
    public List<Film> films(@RequestParam(value="filter", required = false, defaultValue = "%") String filter, @RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {

        if(!DatabaseController.getInstance().checkToken(token, username))
            return null;
            
        List<Film> films = DatabaseController.getInstance().getFilms(filter);

        return films;
    }

    @GetMapping("/api/checkToken")
    public Boolean checkToken(@RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {
        return DatabaseController.getInstance().checkToken(token, username);
    }

    @GetMapping("/api/checkAdmin")
    public Boolean checkAdmin(@RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {
        return DatabaseController.getInstance().checkAdmin(token, username);
    }

    @GetMapping("/api/film")
    public Film film(@RequestParam(value="id", required = true) String id, @RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {

        if(!DatabaseController.getInstance().checkToken(token, username))
            return null;

        Film films = DatabaseController.getInstance().getFilmById(id);

        return films;
    }

    @GetMapping("/api/removeFilm")
    public void removeFilm(@RequestParam(value="id", required = true) String id, @RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {

        if(DatabaseController.getInstance().checkAdmin(token, username)){
            String path = DatabaseController.getInstance().getFilmById(id).imagePath;

            if(path != null){
                File file = new File("src\\main\\resources\\static\\images\\" + path);
                if (file.exists()) {
                    file.delete();
                }
            }

            DatabaseController.getInstance().removeFilm(id);
        }
    }

    @GetMapping("/api/addFilm")
    public void addFilm(@RequestParam(value="imagePath", required = true) String imagePath, @RequestParam(value="genre", required = true) String genre, @RequestParam(value="title", required = true) String title, @RequestParam(value="description", required = true) String description, @RequestParam(value="release_date", required=true) String release_date, @RequestParam(value="token", required = true) String token, @RequestParam(value="username", required = true) String username) throws SQLException {

        if(DatabaseController.getInstance().checkAdmin(token, username)){
            DatabaseController.getInstance().addFilm(title, description, release_date, genre, imagePath);
        }
    }
}
