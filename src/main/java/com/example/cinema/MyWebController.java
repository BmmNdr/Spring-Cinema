package com.example.cinema;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;

@Controller
public class MyWebController {

    private final RestTemplate restTemplate;

    public MyWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/")
    public String landing() {
        return "Login";
    }

    @RequestMapping("/home")
    public String home(@RequestParam(value = "token", required = false) String token, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "filter", required = false, defaultValue = "%") String filter, Model model) {
        if (checkToken(token, username)) {
            String apiUrl = "http://localhost:8080/api/films?token=" + token + "&filter=" + filter + "&username=" + username;

            @SuppressWarnings("unchecked")
            List<Film> items = restTemplate.getForObject(apiUrl, List.class);
            model.addAttribute("films", items);
            model.addAttribute("token", token);
            model.addAttribute("username", username);


            model.addAttribute("isAdmin", checkAdmin(token, username));

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

    @RequestMapping("/film/{id}")
    public String film(@PathVariable String id, @RequestParam(value = "token", required = false) String token, @RequestParam(value = "username", required = false) String username, Model model) throws SQLException {

        if (!checkToken(token, username)) {
            return "Login";
        }

        String apiUrl = "http://localhost:8080/api/film?id=" + id + "&token=" + token + "&username=" + username;
        Film items = restTemplate.getForObject(apiUrl, Film.class);
        model.addAttribute("film", items);
        model.addAttribute("token", token);
        model.addAttribute("username", username);

        return "Film";
    }

    @RequestMapping("/addFilm")
    public String addFilm(@RequestParam String token, @RequestParam String username, Model model) {
        if (!checkAdmin(token, username)) {
            return "Login";
        }

        model.addAttribute("token", token);
        model.addAttribute("username", username);

        return "addFilm";
    }

    private Boolean checkToken(String token, String username) {

        try {
            String apiUrl = "http://localhost:8080/api/checkToken?token=" + token + "&username=" + username;
            return restTemplate.getForObject(apiUrl, Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean checkAdmin(String token, String username) {

        try {
            String apiUrl = "http://localhost:8080/api/checkAdmin?token=" + token + "&username=" + username;
            return restTemplate.getForObject(apiUrl, Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }
}
