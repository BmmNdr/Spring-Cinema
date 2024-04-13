// This class represents a controller for the cinema database.
package com.example.cinema.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.cinema.entity.Film;

public class DatabaseController {
    private static DatabaseController __instance = null;
    private Connection conn;

    // Get the instance of the DatabaseController.
    public static DatabaseController getInstance() throws SQLException {
        if (__instance == null) {
            __instance = new DatabaseController();
        }

        return __instance;
    }

    private DatabaseController() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/cinemaBiemmi", "root", "");
    }

    // Check if a user with the given username or email already exists in the
    // database.
    public boolean checkUserExists(String username, String email) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("SELECT * FROM Utenti WHERE username = ? OR email = ?");

        stat.setString(1, username);
        stat.setString(2, email);

        ResultSet rs = stat.executeQuery();

        if (rs.next()) {
            stat.close();
            return true;
        } else {
            stat.close();
            return false;
        }
    }

    // Save a new user to the database.
    public void saveNewUser(String username, String password, String email) throws SQLException {
        PreparedStatement stat = conn
                .prepareStatement("INSERT INTO Utenti (username, password, email, isAdmin) VALUES (?, md5(?), ?, 0)");

        stat.setString(1, username);
        stat.setString(2, password);
        stat.setString(3, email);

        stat.executeUpdate();

        stat.close();
    }

    /**
     * Check if the given username and password match a user in the database.
     * If the credentials are valid, generate a session token for the user and adds
     * it to the database.
     */
    public String checkPassword(String username, String password) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("SELECT * FROM Utenti WHERE username = ? AND password = md5(?)");

        stat.setString(1, username);
        stat.setString(2, password);

        ResultSet rs = stat.executeQuery();

        if (rs.next()) {
            stat.close();
            return generateSessionToken(username, password);
        } else {
            stat.close();
            return null;
        }
    }

    // Generate a session token for the given username and password.
    private String generateSessionToken(String username, String password) {
        // Concatenate username, password, and current time
        String input = username + password + new Date().toString();

        try {
            // Create MessageDigest instance for MD5 hashing
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Add input string to digest
            md.update(input.getBytes());
            // Get the hash's bytes
            byte[] bytes = md.digest();
            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get the list of films from the database, filtering by genre.
    public List<Film> getFilms(String filter) {
        List<Film> films = new ArrayList<Film>();

        try {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM movies WHERE genre LIKE ?");

            filter = "%" + filter + "%";
            stat.setString(1, filter);

            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                films.add(Film.fromResultSet(rs));
            }

            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return films;
    }

    // Gets a single film from the database by its id
    public Film getFilmById(String id) {
        try {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM movies WHERE id = ?");

            stat.setString(1, id);

            ResultSet rs = stat.executeQuery();

            if (rs.next()) {
                return Film.fromResultSet(rs);
            }

            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Check if the given token and username match a user in the database
    public Boolean checkToken(String token) throws SQLException {

        if (token == null)
            return false;

        PreparedStatement stat = conn.prepareStatement("SELECT * FROM Utenti WHERE token = ?");
        stat.setString(1, token);

        ResultSet rs = stat.executeQuery();

        if (rs.next()) {
            stat.close();
            return true;
        } else {
            stat.close();
            return false;
        }
    }

    // Check if the given token and username match an admin user in the database
    public Boolean checkAdmin(String username) throws SQLException {

        if (username == null)
            return false;

        PreparedStatement stat = conn
                .prepareStatement("SELECT * FROM Utenti WHERE username = ? AND isAdmin = 1");
        stat.setString(1, username);

        ResultSet rs = stat.executeQuery();

        if (rs.next()) {
            stat.close();
            return true;
        } else {
            stat.close();
            return false;
        }
    }

    // Removes a film from the database by its id
    public void removeFilm(String id) {
        try {
            PreparedStatement stat = conn.prepareStatement("DELETE FROM movies WHERE id = ?");

            stat.setString(1, id);

            stat.executeUpdate();

            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Adds a film to the database
    public void addFilm(String title, String description, String release_date, String genre, String imagePath) {
        try {
            PreparedStatement stat = conn.prepareStatement(
                    "INSERT INTO movies (title, description, release_year, genre, imagePath) VALUES (?, ?, ?, ?, ?)");

            stat.setString(1, title);
            stat.setString(2, description);
            stat.setString(3, release_date);
            stat.setString(4, genre);
            stat.setString(5, imagePath);

            stat.executeUpdate();

            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}