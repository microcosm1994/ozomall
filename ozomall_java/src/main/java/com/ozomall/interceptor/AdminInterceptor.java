package com.ozomall.interceptor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理系统拦截器
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @return 返回true才会继续向下执行，返回false取消当前请求
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            String tokenJsonValue = redisTemplate.opsForValue().get(token);
            if (!StringUtils.isEmpty(tokenJsonValue)) {
                return true;
            } else {
                response.sendError(401, "登陆状态已过期，请重新登陆。");
                return false;
            }
        } else {
            response.sendError(401, "登陆状态已过期，请重新登陆。");
            return false;
        }
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
    }

    /**
     * 在整个请求结束之后被调用，DispatcherServlet 渲染视图之后执行（进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
    }

}
