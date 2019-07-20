package com.qf.service;

import com.qf.pojo.User;
import org.apache.ibatis.annotations.Param;


public interface UserService {
    Integer checkUsername(@Param("username")String username);

    Integer register(User user);

    //执行登陆
    User login(String username, String password);
}
