package com.qf.service.impl;

import com.qf.mapper.UserMapper;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Integer checkUsername(String username) {
        if(!StringUtils.isEmpty(username)){
            username = username.trim();
        }
        return userMapper.findCountByUsername(username);
    }

    @Override
    public Integer register(User user) {
        String newPwd = new Md5Hash(user.getPassword() ,null,1024).toString();
        user.setPassword(newPwd);
        Integer count = userMapper.save(user);
        return count;
    }

    @Override
    public User login(String username, String password) {
        //根据用户名查询用户信息
        User user = userMapper.findByUsername(username);
        //2判断查询结果是否为null
        if(user!=null){
            //2.1如果不为null,判断密码
            String newPwd = new Md5Hash(password,null,1024).toString();
            //3如果密码正确.直接返回查询到user
            if(user.getPassword().equals(newPwd)){
                return user;
            }
        }

        //4.其他情况一律为null
        return null;
    }
}
