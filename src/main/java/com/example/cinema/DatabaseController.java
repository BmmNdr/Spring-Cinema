package com.example.cinema;

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

public class DatabaseController {
    private static DatabaseController __instance = null;
    private Connection conn;

    public static DatabaseController getInstance() throws SQLException {
        if (__instance == null) {
            __instance = new DatabaseController();
        }

        return __instance;
    }

    private DatabaseController() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema", "root", "");
    }

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

    public void saveNewUser(String username, String password, String email) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("INSERT INTO Utenti (username, password, email, isAdmin) VALUES (?, md5(?), ?, 0)");

        stat.setString(1, username);
        stat.setString(2, password);
        stat.setString(3, email);

        stat.executeUpdate();

        stat.close();
    }

    public String checkPassword(String username, String password) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("SELECT * FROM Utenti WHERE username = ? AND password = md5(?)");

        stat.setString(1, username);
        stat.setString(2, password);

        ResultSet rs = stat.executeQuery();

        if (rs.next()) {
            stat.close();
            String token = generateSessionToken(username, password);

            stat = conn.prepareStatement("UPDATE Utenti SET token = ? WHERE username = ?");
            stat.setString(1, token);
            stat.setString(2, username);

            stat.executeUpdate();

            stat.close();
            return token;
        } else {
            stat.close();
            return null;
        }
    }

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
            // Get complete hashed session token
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    public Boolean checkToken(String token, String username) throws SQLException {

        PreparedStatement stat = conn.prepareStatement("SELECT * FROM Utenti WHERE username = ? AND token = ?");
        stat.setString(1, username);
        stat.setString(2, token);

        ResultSet rs = stat.executeQuery();

        if (rs.next()) {
            stat.close();
            return true;
        } else {
            stat.close();
            return false;
        }
    }

    public Boolean checkAdmin(String token, String username) throws SQLException {

        PreparedStatement stat = conn.prepareStatement("SELECT * FROM Utenti WHERE username = ? AND token = ? AND isAdmin = 1");
        stat.setString(1, username);
        stat.setString(2, token);

        ResultSet rs = stat.executeQuery();

        if (rs.next()) {
            stat.close();
            return true;
        } else {
            stat.close();
            return false;
        }
    }

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

    public void addFilm(String title, String description, String release_date, String genre, String imagePath) {
        try {
            PreparedStatement stat = conn.prepareStatement("INSERT INTO movies (title, description, release_year, genre, imagePath) VALUES (?, ?, ?, ?, ?)");

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