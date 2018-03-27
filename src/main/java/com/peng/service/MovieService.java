package com.peng.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.peng.mapper.MovieMapper;
import com.peng.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PengJK on 2018/3/24.
 */
@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;
    
    public Movie add(Movie movie) {
        movieMapper.insert(movie);
        return movie;
    }

    public Movie get(long uuid) {
        Movie movie = movieMapper.selectById(uuid);
        if(movie != null) {
            return movie;
        }
        return null;
    }

    public Movie update(Movie movie) {
        movieMapper.updateById(movie);
        return movie;
    }

    public Page<Movie> search(String movieName, String movieTag, int current, int size) {
        Page<Movie> page = new Page<>(current, size);
        List<Movie> movieList = movieMapper.selectMovieList(page,movieName,movieTag);
        return page.setRecords(movieList);
    }

}
