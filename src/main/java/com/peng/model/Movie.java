package com.peng.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * Created by 64471 on 2018/3/24.
 */
@TableName("movie")
public class Movie {

    //固定主键
    @TableId
    private Long uuid;

    @TableField(value = "moviename")
    private String movieName;

    @TableField(value = "movietag")
    private String movieTag;

    @TableField(value = "moiveposter")
    private String moviePoster;

    @TableField(value = "moviesynopsis")
    private String movieSynopsis;

    @TableField(value = "moviesphoto")
    private String moviePhoto;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieTag() {
        return movieTag;
    }

    public void setMovieTag(String movieTag) {
        this.movieTag = movieTag;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieSynopsis() {
        return movieSynopsis;
    }

    public void setMovieSynopsis(String movieSynopsis) {
        this.movieSynopsis = movieSynopsis;
    }

    public String getMoviePhoto() {
        return moviePhoto;
    }

    public void setMoviePhoto(String moviePhoto) {
        this.moviePhoto = moviePhoto;
    }
}
