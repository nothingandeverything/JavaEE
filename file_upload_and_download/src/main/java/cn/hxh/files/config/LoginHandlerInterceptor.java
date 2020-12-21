package cn.hxh.files.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录成功 有session
        Object user_info = request.getSession().getAttribute("USER_INFO");
        if (user_info == null){
            request.getRequestDispatcher("/").forward(request,response);
            return false;
        }else {
            return true;
        }

    }
}
