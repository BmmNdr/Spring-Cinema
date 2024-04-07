package com.example.cinema.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieManager {

    static public void writeCookie(HttpServletResponse response, String name, String value) {
        // create a cookie
        Cookie cookie = new Cookie(name, value);

        // add cookie to response
        response.addCookie(cookie);
    }

    static public void writeCookie(HttpServletResponse response, String name, String value, int setMaxAge) {
        // create a cookie
        Cookie cookie = new Cookie(name, value);

        cookie.setMaxAge(setMaxAge);

        // add cookie to response
        response.addCookie(cookie);
    }

    static public String readCookie(HttpServletRequest request, String name) {

        // add cookie to response
        Cookie c = getCookie_(request, name);
        if (c == null) {
            return "Cookie non settato!";
        } else {
            return "Username: " + c.getValue();
        }
    }


    static public Cookie getCookie_(HttpServletRequest r, String name) {

        Cookie[] cookies = r.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(name))
                    return cookies[i];
            }
        }
        return null;
    }

    // per rimuovere un cookie, occorre settare il campo "MaxAge" a 0, in modo da
    // farlo scadere ed il browser lo cancellerà
    static public void removeCookie(HttpServletResponse request, String name) {
        Cookie c = new Cookie(name, null);
        c.setMaxAge(0);
        request.addCookie(c);
    }
}
