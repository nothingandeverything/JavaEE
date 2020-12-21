package cn.hxh.files.service.impl;

import cn.hxh.files.mapper.UserDao;
import cn.hxh.files.pojo.User;
import cn.hxh.files.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020年12月13日22:29:26
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User query(String username, String password) {
        return userDao.query(username,password);
    }
}