package com.example.cinema.controller;

import java.sql.SQLException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.cinema.entity.Film;
import com.example.cinema.service.Service;
import com.example.cinema.service.SessionManager;

import org.springframework.ui.Model;

@Controller
public class MyWebController {

    @RequestMapping("/")
    public String landing() {
        return "Login";
    }

    // Home page only if the user is logged in.
    // Otherwise it will redirect to the login page.
    // The filter parameter is % by default, so it will match all the films in the
    // SELECT WHERE LIKE statement. (Redundant default value in the REST API, just
    // to be shure)
    @RequestMapping("/home")
    public String home(Model model, HttpServletRequest request) {

        if (SessionManager.session_exists(request, "token")) {
            model.addAttribute("isAdmin", SessionManager.admin_session_exists(request, "token"));

            return "Home";
        }

        return "Login";
    }

    @RequestMapping("/login")
    public String login() {
        return "Login";
    }

    @RequestMapping("/register")
    public String register() {
        return "Register";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse request) {
        SessionManager.session_destroy(request);

        return "Login";
    }

    // Film page only if the user is logged in.
    // Film id is passed as a path variable. (in the URL)
    @RequestMapping("/film/{id}")
    public String film(@PathVariable String id, Model model, HttpServletRequest request) throws SQLException {

        if (!SessionManager.session_exists(request, "token")) {
            return "Login";
        }

        Film film = Service.getInstance().getFilmById(request, id);

        model.addAttribute("film", film);

        return "Film";
    }

    // Add film page only if the user is logged in and is an admin.
    @RequestMapping("/addFilm")
    public String addFilm(Model model, HttpServletRequest request) {

        if (!SessionManager.admin_session_exists(request, "token")) {
            return "Login";
        }

        return "addFilm";
    }
}
