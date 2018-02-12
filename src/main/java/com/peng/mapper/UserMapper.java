package com.peng.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.peng.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by PengJK on 2018/1/18.
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User>{
}
