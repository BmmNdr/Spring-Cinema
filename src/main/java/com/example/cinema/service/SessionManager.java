package com.example.cinema.service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

public class SessionManager {
    static public void session_start(String token, Boolean isAdmin) {
        HttpSession s = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        
        s.setAttribute("token", token);
        s.setAttribute("isAdmin", isAdmin);

        //CookieManager.writeCookie("token", token);
    }

    static public void session_destroy() {

        HttpSession s = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        s.invalidate();

        //CookieManager.removeCookie("token");
    }

    static public Boolean session_exists() {
        HttpSession s = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        String token =(String)s.getAttribute("token");

        return !(token == null);


        //Cookie c = CookieManager.getCookie_("token");
        //if (c == null) {
        //    return false;
        //}
        //String value = c.getValue();
        //return Service.chkToken(value);
    }

    static public Boolean admin_session_exists() {

        HttpSession s = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        Boolean isAdmin =(Boolean)s.getAttribute("isAdmin");

        if (isAdmin == null) {
            return false;
        }

        return isAdmin;
    }
}
