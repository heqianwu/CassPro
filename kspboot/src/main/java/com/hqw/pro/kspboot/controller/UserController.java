package com.hqw.pro.kspboot.controller;

import com.hqw.pro.kspboot.pojo.User;
import com.hqw.pro.kspboot.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserRepos userRepos;

    @RequestMapping(value="saveUser")
    public User saveUser(){
        return userRepos.save(new User());
    }

    @RequestMapping(value="/findByUserName")
    public List<User> findByUserName(String userName){
        return userRepos.findByUserName(userName);
    }

    @RequestMapping(value="findByUserNameLike")
    public List<User> findByUserNameLkie(String userName){
        return userRepos.findByUserNameLike(userName);
    }

    @RequestMapping(value="findByPage")
    public Page<User> findByPage(Integer userType){
        return userRepos.findByUserType(userType, new PageRequest(1, 5));
    }

}