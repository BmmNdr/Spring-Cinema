package com.example.cinema.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.cinema.entity.Film;

import org.springframework.ui.Model;

//Logica Utilizzata (troppo lunga e "complessa" per scrivere in inglese)
//Il WebController effettua chiamate REST all'API per ottenere i dati necessari (film, token, ecc.) o per effettuare operazioni (aggiunta di un film, controllo token).
//Il controller è in grado di effettuare chiamate REST grazie all'oggetto RestTemplate.
//Ho utilizzato questa logica per mantenere il codice del controller più pulito e per separare la logica di business dal controller.
//Inoltre, non e' detto che l'API sia sempre disponibile sullo stesso server del controller, quindi e' meglio utilizzare chiamate REST per ottenere i dati.
//Infine, solo l'API ha accesso al database. Il controller non ha accesso diretto al database, quindi deve chiamare l'API per ottenere i dati.

@Controller
public class MyWebController {

    private final RestTemplate restTemplate;

    //Use to make the RestTemplate object available to the controller.
    public MyWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/")
    public String landing() {
        return "Login";
    }

    //Home page only if the user is logged in.
    //Otherwise it will redirect to the login page.
    //The filter parameter is % by default, so it will match all the films in the SELECT WHERE LIKE statement. (Redundant default value in the REST API, just to be shure)
    @RequestMapping("/home")
    public String home(@RequestParam(value = "token", required = false) String token, @RequestParam(value = "username", required = false) String username, Model model) {
        if (checkToken(token, username)) {
            //I need to add token and username to the model so I can add them to the links in the page and mantain the session.
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

    //Film page only if the user is logged in.
    //Film id is passed as a path variable. (in the URL)
    @RequestMapping("/film/{id}")
    public String film(@PathVariable String id, @RequestParam(value = "token", required = false) String token, @RequestParam(value = "username", required = false) String username, Model model) throws SQLException {

        if (!checkToken(token, username)) {
            return "Login";
        }

        //Call the API to get the film by id
        String apiUrl = "http://localhost:8080/api/film?id=" + id + "&token=" + token + "&username=" + username;
        Film items = restTemplate.getForObject(apiUrl, Film.class);

        model.addAttribute("film", items);
        model.addAttribute("token", token);
        model.addAttribute("username", username);

        return "Film";
    }

    //Add film page only if the user is logged in and is an admin.
    @RequestMapping("/addFilm")
    public String addFilm(@RequestParam String token, @RequestParam String username, Model model) {
        if (!checkAdmin(token, username)) {
            return "Login";
        }

        model.addAttribute("token", token);
        model.addAttribute("username", username);

        return "addFilm";
    }

    //Calls the API to check if the token is valid.
    private Boolean checkToken(String token, String username) {

        try {
            String apiUrl = "http://localhost:8080/api/checkToken?token=" + token + "&username=" + username;
            return restTemplate.getForObject(apiUrl, Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }

    //Calls the API to check if the user is an admin.
    private Boolean checkAdmin(String token, String username) {

        try {
            String apiUrl = "http://localhost:8080/api/checkAdmin?token=" + token + "&username=" + username;
            return restTemplate.getForObject(apiUrl, Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }
}
