package com.sinosoft.nlg.lpjpa.common.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/6/26.
 * 实现拦截器处理适配器 HandlerInterceptorAdapter
 * 或者实现拦截处理器  HandlerInterceptor
 */

public class DemoInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
      request.removeAttribute("startTime");
       long endTime = System.currentTimeMillis();
       Long costTime=  endTime-startTime;
       System.out.print("本次请求时间为:"+costTime+"ms");
       request.setAttribute("handlingTime",costTime);
    }
}
