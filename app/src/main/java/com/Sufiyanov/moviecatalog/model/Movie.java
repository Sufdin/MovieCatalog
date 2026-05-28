package com.Sufiyanov.moviecatalog.model;

import java.io.Serializable;

public class Movie implements Serializable {

    private int id;
    private String title;
    private int year;
    private String genre;
    private float rating;
    private String description;
    private String status;

    // Конструктор для создания нового фильма (без ID, его выдаст БД)
    public Movie(String title, int year, String genre, float rating, String description, String status) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.status = status;
    }

    // Конструктор для получения фильма из БД (с ID)
    public Movie(int id, String title, int year, String genre, float rating, String description, String status) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
        this.status = status;
    }

    // Геттеры и Сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}