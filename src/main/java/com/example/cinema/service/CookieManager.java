package com.example.cinema.service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Not Use, only for example if you don't want to use the session
public class CookieManager {

    static public void writeCookie(String name, String value) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        // create a cookie
        Cookie cookie = new Cookie(name, value);

        // add cookie to response
        response.addCookie(cookie);
    }

    static public void writeCookie(String name, String value, int setMaxAge) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        // create a cookie
        Cookie cookie = new Cookie(name, value);

        cookie.setMaxAge(setMaxAge);

        // add cookie to response
        response.addCookie(cookie);
    }

    static public String readCookie(String name) {
        // add cookie to response
        Cookie c = getCookie_(name);
        if (c == null) {
            return "Cookie non settato!";
        } else {
            return "Username: " + c.getValue();
        }
    }

    static public Cookie getCookie_(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        Cookie[] cookies = request.getCookies();
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
    static public void removeCookie(String name) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();

        Cookie c = new Cookie(name, null);
        c.setMaxAge(0);
        response.addCookie(c);
    }
}
