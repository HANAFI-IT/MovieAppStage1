package com.example.amiraahabeeb.ntl_mouvie_app;

/**
 * Created by Amira A. habeeb on 30/03/2017.
 */
public class Mouvie_data {
    String poster_path;
    String overview;
    String release_date;
    String original_title;
    String popularity;
    String id;
    String content;

    public Mouvie_data(String poster_path , String  overview , String release_date, String original_title,String popularity ,String id){
        this.poster_path=poster_path;
        this.overview=overview;
        this.release_date=release_date;
        this.original_title=original_title;
        this.popularity=popularity;
        this.id=id;


    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getid() {
        return id;
    }


    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setid(String id) {
        this.id = id;
    }
}


