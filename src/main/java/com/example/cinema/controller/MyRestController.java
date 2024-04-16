package com.example.cinema.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.cinema.entity.Film;
import com.example.cinema.service.Service;

@RestController
public class MyRestController {

    @GetMapping("/apilogin")
    public String APIlogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        return Service.getInstance().checkLogin(username, password).toString();
    }

    @GetMapping("/apiregister")
    public String APIregister(@RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("email") String email) {

        return Service.getInstance().checkRegister(username, password, email).toString();
    }

    // Get the list of films from the database only if the token is valid.
    // The filter parameter is % by default, so it will match all the films in the
    // SELECT WHERE LIKE statement.
    @GetMapping("/apifilms")
    public List<Film> APIfilms(@RequestParam(value = "filter", required = false, defaultValue = "%") String filter) {

        return Service.getInstance().getFilms(filter);
    }

    // Gets a single film from the database by its id only if the token is valid.
    @GetMapping("/apifilm")
    public Film APIfilm(@RequestParam(value = "id", required = true) String id) {

        return Service.getInstance().getFilmById(id);
    }

    // Removes a film from the database only if user is an admin
    @GetMapping("/apiremoveFilm")
    public void APIremoveFilm(@RequestParam(value = "id", required = true) String id) {

        Service.getInstance().removeFilm(id);
    }

    // Adds a film to the database only if user is an admin
    @GetMapping("/apiaddFilm")
    public void APIaddFilm(@RequestParam(value = "imagePath", required = true) String imagePath,
            @RequestParam(value = "genre", required = true) String genre,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "description", required = true) String description,
            @RequestParam(value = "release_date", required = true) String release_date) {

        Service.getInstance().addFilm(title, description, release_date, genre, imagePath);
    }

    // Uploads an image to the server.
    // Only accessible by admin after addFilm.
    @PostMapping(value = "/apiuploadPoster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void APIuploadPoster(@RequestParam("file") MultipartFile file) throws IOException {
        // Get the file name
        String fileName = file.getOriginalFilename();

        // Save the file to the server
        File newFile = new File("src\\main\\resources\\static\\images\\" + fileName);
        newFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(file.getBytes());
        fos.close();
    }
}