package com.example.cinema.service;

import jakarta.servlet.http.Cookie;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionManager {
    static public void session_start(String token) {
        HttpServletResponse r = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        CookieManager.writeCookie(r, "token", token);
    }

    static public void session_destroy() {
        HttpServletResponse r = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieManager.removeCookie(r, "token");
    }

    static public Boolean session_exists(String name) {
        HttpServletRequest r = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Cookie c = CookieManager.getCookie_(r, name);

        if (c == null) {
            return false;
        }

        String value = c.getValue();

        return Service.chkToken(value);
    }

    static public Boolean admin_session_exists(String name) {
        HttpServletRequest r = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Cookie c = CookieManager.getCookie_(r, name);
        String value = c.getValue();

        return Service.chkAdmin(value);
    }
}
