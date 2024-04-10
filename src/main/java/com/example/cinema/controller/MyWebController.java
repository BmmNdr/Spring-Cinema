package com.example.cinema.controller;

import java.sql.SQLException;

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
    public String home(Model model) {
        if (SessionManager.session_exists("token")) {
            model.addAttribute("isAdmin", SessionManager.admin_session_exists("token"));

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

    // Film page only if the user is logged in.
    // Film id is passed as a path variable. (in the URL)
    @RequestMapping("/film/{id}")
    public String film(@PathVariable String id, Model model) throws SQLException {

        if (!SessionManager.session_exists("token")) {
            return "Login";
        }

        Film film = Service.getInstance().getFilmById(id);

        model.addAttribute("film", film);

        return "Film";
    }

    // Add film page only if the user is logged in and is an admin.
    @RequestMapping("/addFilm")
    public String addFilm(Model model) {
        if (!SessionManager.admin_session_exists("token")) {
            return "Login";
        }

        return "addFilm";
    }
}
