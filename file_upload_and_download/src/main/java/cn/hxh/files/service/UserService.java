package cn.hxh.files.service;

import cn.hxh.files.pojo.User;


public interface UserService {

    User query(String username, String password);
}