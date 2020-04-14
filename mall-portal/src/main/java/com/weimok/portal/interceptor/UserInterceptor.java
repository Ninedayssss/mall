package com.weimok.portal.interceptor;

import com.weimok.auth.entity.UserInfo;
import com.weimok.auth.utils.JwtUtils;
import com.weimok.common.utils.CookieUtils;
import com.weimok.portal.config.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author itsNine
 * @create 2020-04-12-9:12
 */
@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {
    private JwtProperties prop;

    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    public UserInterceptor(JwtProperties prop) {
        this.prop = prop;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        //获取cookie中的token
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        try {
            System.out.println("拦截器执行");
            System.out.println("token"+token);
            //解析token
            UserInfo user = JwtUtils.getInfoFromToken(token, prop.getPublicKey());

            //传递userden
            tl.set(user);

            //放行
            return true;
        }catch (Exception e){
            log.error("解析用户身份失败",e);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //最后用完，清空数据
        tl.remove();
    }

    public static UserInfo getUser(){
        return tl.get();
    }
}
