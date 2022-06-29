package com.example.kill.service.impl;

import com.example.kill.exception.GlobalException;
import com.example.kill.pojo.LoginParam;
import com.example.kill.redis.RedisService;
import com.example.kill.redis.UserKey;
import com.example.kill.result.CodeMsg;
import org.apache.commons.lang3.StringUtils;
import com.example.kill.dao.UserMapper;
import com.example.kill.pojo.User;
import com.example.kill.service.UserService;
import com.example.kill.utils.MD5Util;
import com.example.kill.utils.UUIDUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    Logger logger=Logger.getLogger(UserServiceImpl.class);


    public String login(HttpServletRequest request,HttpServletResponse response, LoginParam loginParam) {
        Cookie[] cookies = request.getCookies();
        String token1=null;
        int i=0;
        if(cookies!=null){
            for(Cookie cookie:cookies) {
                logger.info(i++);
                if (cookie.getName().equals("token")) {
                    token1 = cookie.getValue();
                }
            }
        }
        User u1=getByToken(response,token1);
        if(u1!=null&&u1.getPhone()==loginParam.getMobile()){
            return token1;
        }
        User user = userMapper.checkPhone(loginParam.getMobile());
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPwd= user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(loginParam.getPassword(), saltDB);
        if(!StringUtils.equals(dbPwd , calcPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
//        user.setPassword(StringUtils.EMPTY);

        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }


    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        //延长有效期
        if(user != null) {
            addCookie(response, token, user);
        }
        return user;
    }


    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token, token, user,UserKey.TOKEN_EXPIRE);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.TOKEN_EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
