package com.example.cinema.service;

import jakarta.servlet.http.Cookie;

public class SessionManager {
    static public void session_start(String token) {
        CookieManager.writeCookie("token", token);
    }

    static public void session_destroy() {
        CookieManager.removeCookie("token");
    }

    static public Boolean session_exists() {

        Cookie c = CookieManager.getCookie_("token");

        if (c == null) {
            return false;
        }

        String value = c.getValue();

        return Service.chkToken(value);
    }

    static public Boolean admin_session_exists() {

        Cookie c = CookieManager.getCookie_("token");
        String value = c.getValue();

        return Service.chkAdmin(value);
    }
}
