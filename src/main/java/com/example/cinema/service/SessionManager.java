package com.example.cinema.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionManager {
    static public void session_start(HttpServletResponse response, String token){
        CookieManager.writeCookie(response, "token", token);
    }

    static public void session_destroy(HttpServletResponse request){
        CookieManager.removeCookie(request, "token");
    }

    static public Boolean session_exists(HttpServletRequest request, String name){
        return CookieManager.getCookie_(request, name) == null;
    }
}
