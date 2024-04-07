package com.example.cinema.entity;

import java.sql.ResultSet;
import java.util.List;

import com.google.gson.Gson;

public class Film {
    public int id;
    public String title;
    public String description;
    public int release_year;
    public String imagePath;
    public String genre;

    public static Film fromResultSet(ResultSet rs) {
        Film f = new Film();
        try {
            f.id = rs.getInt("id");
            f.title = rs.getString("title");
            f.description = rs.getString("description");
            f.release_year = rs.getInt("release_year");
            f.imagePath = rs.getString("imagePath");
            f.genre = rs.getString("genre");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }

    public static String toJSON(List<Film> films) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Film f : films) {
            sb.append(new Gson().toJson(f).toString());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
