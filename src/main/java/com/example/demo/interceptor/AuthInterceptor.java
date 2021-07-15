package com.example.demo.interceptor;

import com.example.demo.service.RedisService;
import com.example.demo.util.ResultReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Resource
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //解决跨域请求，options方法直接返回true
        if (request.getMethod().equals("OPTIONS"))
            return true;

        String token = request.getHeader("token");
        logger.info("token: "+ token);

        if (StringUtils.isEmpty(token)) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json; charset=utf-8");
            String jsonStr = "{\"code\":-2,\"msg\":\"用户未登录！\"}";
            response.getWriter().write(jsonStr);
            return false;
        }
        Object loginStatus = redisService.get(token);
        if( Objects.isNull(loginStatus)){
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json; charset=utf-8");
            String jsonStr = "{\"code\":-2,\"msg\":\"登陆失效，请重新登陆！\"}";
            response.getWriter().write(jsonStr);
            return false;
        }
        return true;
    }
}
