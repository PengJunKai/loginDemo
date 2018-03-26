package com.peng.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by 64471 on 2018/3/24.
 */
@ApiModel(value = "电影")
public class MovieVO {

    @ApiModelProperty(value = "唯一Id")
    private Long uuid;

    @ApiModelProperty(value = "电影名字")
    private String movieName;

    @ApiModelProperty(value = "电影Tag")
    private String movieTag;

    @ApiModelProperty(value = "电影简介")
    private String movieSynopsis;

    @ApiModelProperty(value = "电影图片")
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
