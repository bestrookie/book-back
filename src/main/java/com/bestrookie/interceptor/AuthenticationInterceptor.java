package com.bestrookie.interceptor;

import com.bestrookie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author : bestrookie
 * @date : 17:01 2020/10/7
 * @description : 拦截器区去获取token并验证
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String token = request.getHeader("authorization");
        if (!(object instanceof HandlerMethod)){
            return true;
        }
        if (token != null && !Objects.equals(token,"null")){
            if (TokenUtils.verify(token)){
                String info = "T"+TokenUtils.getInfo(token);
                if (token.equals(redisTemplate.opsForValue().get(info))){
                    return true;
                }else {
                    response.setStatus(413);
                }
            }else {
                response.setStatus(403);
            }
        }else {
            response.setStatus(414);
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
