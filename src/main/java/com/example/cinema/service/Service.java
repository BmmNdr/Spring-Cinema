package com.example.cinema.service;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

import com.example.cinema.controller.DatabaseController;
import com.example.cinema.entity.Film;

public class Service {
    private static Service __instance = null;

    private DatabaseController db = null;

    // Get the instance of the DatabaseController.
    public static Service getInstance() {
        if (__instance == null) {
            __instance = new Service();
        }

        return __instance;
    }

    private Service() {
        try {
            db = DatabaseController.getInstance();
        } catch (SQLException e) {
            db = null;
            System.out.println("Error Creating db connection!");
        }
    }

    public static Boolean chkToken(String token) {
        try {
            return DatabaseController.getInstance().checkToken(token);
        } catch (SQLException e) {
            return false;
        }
    }

    public static Boolean chkAdmin(String token) {
        try {
            return DatabaseController.getInstance().checkAdmin(token);
        } catch (SQLException e) {
            return false;
        }
    }

    public JSONObject checkLogin(String username, String password) {
        JSONObject response = new JSONObject();

        // Check if the password is correct. If it is, the token is returned and added
        // to the database
        String token = null;
        try {
            token = db.checkPassword(username, password);

            if (token != null)
                SessionManager.session_start(token);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error checking password!");
        }

        // Status is true if the token is not null.
        response.put("status", token != null);

        return response;
    }

    public JSONObject checkRegister(String username, String password, String email) {
        JSONObject response = new JSONObject();

        // Check if the user already exists in the database
        try {
            Boolean userExists = db.checkUserExists(username, email);

            if (userExists)
                response.put("status", false);
            else {
                // if the users does not exist, save it to the database
                DatabaseController.getInstance().saveNewUser(username, password, email);
                response.put("status", true);
            }
        } catch (SQLException e) {
            response.put("status", false);
        }

        return response;
    }

    public List<Film> getFilms(String filter) {

        if (!SessionManager.session_exists())
            return null;

        List<Film> films = db.getFilms(filter);

        return films;
    }

    public Film getFilmById(String id) {
        if (!SessionManager.session_exists())
            return null;

        Film film = db.getFilmById(id);

        return film;
    }

    public void removeFilm(String id) {
        if (!SessionManager.admin_session_exists())
            return;

        String path = db.getFilmById(id).imagePath;

        // Remove the image from the server
        if (path != null) {
            File file = new File("src\\main\\resources\\static\\images\\" + path);
            if (file.exists()) {
                file.delete();
            }
        }

        db.removeFilm(id);
    }

    public void addFilm(String title, String description, String release_date, String genre, String imagePath) {
        if (!SessionManager.admin_session_exists())
            return;

        db.addFilm(title, description, release_date, genre, imagePath);
    }
}