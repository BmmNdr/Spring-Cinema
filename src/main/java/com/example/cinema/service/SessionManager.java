package com.example.cinema.service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

public class SessionManager {

    private static String sessionCookieName = "token";
    private static String adminCookieName = "isAdmin";

    static public void session_start(String token, Boolean isAdmin) {
        HttpSession s = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getSession();

        s.setAttribute(sessionCookieName, token);
        s.setAttribute(adminCookieName, isAdmin);

        // CookieManager.writeCookie(sessionCookieName, token);
    }

    static public void session_destroy() {

        HttpSession s = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getSession();

        s.invalidate();

        // CookieManager.removeCookie("token");
    }

    static public Boolean session_exists() {
        HttpSession s = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getSession();

        String token = (String) s.getAttribute(sessionCookieName);

        return !(token == null);

        // Cookie c = CookieManager.getCookie_(sessionCookieName);
        // if (c == null) {
        // return false;
        // }
        // String value = c.getValue();
        // return Service.chkToken(value);
    }

    static public Boolean admin_session_exists() {

        HttpSession s = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getSession();

        Boolean isAdmin = (Boolean) s.getAttribute(adminCookieName);

        if (isAdmin == null) {
            return false;
        }

        return isAdmin;
    }
}
