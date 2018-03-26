package com.peng.service;

import com.peng.mapper.MovieMapper;
import com.peng.model.Movie;
import com.peng.vo.MovieVO;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by 64471 on 2018/3/24.
 */
@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;
    
    public MovieVO add(MovieVO movieVO) throws InvocationTargetException, IllegalAccessException {

        Movie movie = new Movie();

        BeanUtils.copyProperties(movie,movieVO);

        movieMapper.insert(movie);

        BeanUtils.copyProperties(movieVO,movie);

        return movieVO;
    }

    public MovieVO get(long uuid) throws InvocationTargetException, IllegalAccessException {

        Movie movie = movieMapper.selectById(uuid);

        if(movie != null) {
            MovieVO movieVO = new MovieVO();
            BeanUtils.copyProperties(movieVO,movie);
            return movieVO;
        }

        return null;
    }

    public MovieVO update(MovieVO movieVO) throws InvocationTargetException, IllegalAccessException {

        Movie movie = new Movie();

        BeanUtils.copyProperties(movie,movieVO);

        movieMapper.updateById(movie);

        BeanUtils.copyProperties(movieVO,movie);

        return movieVO;

    }
}
