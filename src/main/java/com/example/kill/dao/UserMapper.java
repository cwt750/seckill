package com.example.kill.dao;

import com.example.kill.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User selectByPhoneAndPassword(@Param("phone") String phone , @Param("password") String password);
    User checkPhone(@Param("phone") String phone );
}
