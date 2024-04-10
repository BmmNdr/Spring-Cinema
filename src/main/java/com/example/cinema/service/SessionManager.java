package com.example.cinema.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionManager {
    static public void session_start(HttpServletResponse r, String token) {
        CookieManager.writeCookie(r, "token", token);
    }

    static public void session_destroy(HttpServletResponse r) {
        CookieManager.removeCookie(r, "token");
    }

    static public Boolean session_exists(HttpServletRequest r, String name) {

        Cookie c = CookieManager.getCookie_(r, name);

        if (c == null) {
            return false;
        }

        String value = c.getValue();

        return Service.chkToken(value);
    }

    static public Boolean admin_session_exists(HttpServletRequest r, String name) {

        Cookie c = CookieManager.getCookie_(r, name);
        String value = c.getValue();

        return Service.chkAdmin(value);
    }
}
