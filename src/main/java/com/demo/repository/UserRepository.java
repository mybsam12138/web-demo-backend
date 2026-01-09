package com.demo.repository;

import com.demo.entity.User;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepository extends BaseMapper<User> {
    
    @Select("SELECT * FROM users WHERE uuid = #{uuid} AND provider = #{provider} LIMIT 1")
    User findByUuidAndProvider(@Param("uuid")String uuid, @Param("provider") String provider);
}

