package com.example.cinema.service;
import java.sql.SQLException;

import org.json.JSONObject;

import com.example.cinema.controller.DatabaseController;

public class Service {
    private static Service __instance = null;

    private DatabaseController db = null;

    // Get the instance of the DatabaseController.
    public static Service getInstance() throws SQLException {
        if (__instance == null) {
            __instance = new Service();
        }

        return __instance;
    }

    private Service(){
        try {
            db = DatabaseController.getInstance();
        } catch (SQLException e) {
            db = null;
            System.out.println("Error Creating db connection!");
        }
    }

    public JSONObject checkLogin(String username, String password){
        JSONObject response = new JSONObject();

        // Check if the password is correct. If it is, the token is returned and added to the database
        String token = null;
        try {
            token = db.checkPassword(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error checking password!");
        }

        //Status is true if the token is not null.
        response.put("status", token != null);
        response.put("token", token);

        return response;
    }
}