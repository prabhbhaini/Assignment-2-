package com.omdb.app.omdb;

public class OMDBData {
    public OMDBData(String title, String studio, int year, int rating, String description,
                    String genre, String imdbId, String poster) {
        this.title = title;
        this.studio = studio;
        this.year = year;
        this.rating = rating;
        this.description = description;
        this.genre = genre;
        this.imdbId = imdbId;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    private String title;
    private String studio;
    private int year;
    private int rating;
    private String description;
    private String genre;
    private String imdbId;
    private String poster;




}
