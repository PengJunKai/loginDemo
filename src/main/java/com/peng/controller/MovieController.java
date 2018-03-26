package com.peng.controller;

import com.peng.service.MovieService;
import com.peng.utils.tips.R;
import com.peng.utils.tips.Tip;
import com.peng.vo.MovieVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

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
    public Tip add(@RequestBody MovieVO movieVO) throws InvocationTargetException, IllegalAccessException {

        movieVO = movieService.add(movieVO);

        return R.success(movieVO);
    }

    @ApiOperation(value = "获取电影信息")
    @GetMapping
    public Tip get(Long uuid) throws InvocationTargetException, IllegalAccessException {
        MovieVO movieVO = movieService.get(uuid);
        if(movieVO == null) {
            return R.error("uuid错误");
        }
        return R.success(movieVO);
    }

    @ApiOperation(value = "更新电影信息")
    @PutMapping
    public Tip update(@RequestBody MovieVO movieVO) throws InvocationTargetException, IllegalAccessException {
        movieVO = movieService.update(movieVO);
        return R.success(movieVO);
    }

    @ApiOperation(value = "删除电影信息")
    @DeleteMapping
    public void delete() {

    }

    @ApiOperation(value = "查询电影")
    @GetMapping("/search")
    public void search(String movieName,String movieTag) {

    }

}
