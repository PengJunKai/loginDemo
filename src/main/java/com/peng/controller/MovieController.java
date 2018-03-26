package com.peng.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.peng.model.Movie;
import com.peng.service.MovieService;
import com.peng.utils.BeanKit;
import com.peng.utils.tips.R;
import com.peng.utils.tips.Tip;
import com.peng.vo.MovieVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by PengJK on 2018/3/24.
 */
@RestController
@RequestMapping(value = "movie")
@Api(value = "电影模块")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @ApiOperation(value = "新增电影")
    @PostMapping
    public Tip add(@RequestBody MovieVO movieVO) {
        Movie movie = movieService.add(toPO(movieVO));
        return R.success(toVO(movie));
    }

    @ApiOperation(value = "获取电影信息")
    @GetMapping
    public Tip get(Long uuid) {
        Movie movie = movieService.get(uuid);
        if(movie == null) {
            return R.error("uuid错误");
        }
        return R.success(toVO(movie));
    }

    @ApiOperation(value = "更新电影信息")
    @PutMapping
    public Tip update(@RequestBody MovieVO movieVO) {
        Movie movie = movieService.update(toPO(movieVO));
        return R.success(toVO(movie));
    }

    @ApiOperation(value = "删除电影信息")
    @DeleteMapping
    public Tip delete(Long uuid) {
        return R.success();
    }

    @ApiOperation(value = "查询电影")
    @GetMapping("/search")
    public Page<Movie> search(String movieName,String movieTag,int current,int size) {

        return movieService.search(current,size);

    }

    private MovieVO toVO(Movie movie) {
        MovieVO movieVO = new MovieVO();
        BeanKit.copyTo(movie,movieVO);
        return movieVO;
    }

    private Movie toPO(MovieVO movieVO) {
        Movie movie = new Movie();
        BeanKit.copyTo(movieVO,movie);
        return movie;
    }

}
