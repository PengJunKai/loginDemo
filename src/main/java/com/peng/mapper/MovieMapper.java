package com.peng.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.peng.model.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PengJK on 2018/1/18.
 */
@Mapper
@Repository
public interface MovieMapper extends BaseMapper<Movie>{

    List<Movie> selectMovieList(Page page, @Param("moviename") String movieName,@Param("movietag") String moiveTag);
}
