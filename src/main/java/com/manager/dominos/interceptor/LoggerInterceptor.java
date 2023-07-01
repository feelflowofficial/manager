package com.manager.dominos.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.InetAddress;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        /*log.info("===============================================");
        log.info("==================== BEGIN ====================");
        log.info("Request URI ===> " + request.getRequestURI());
        return HandlerInterceptor.super.preHandle(request, response, object);*/

        HttpServletRequest httpRequest  = (HttpServletRequest) request;

        log.info("################ REQUEST ################");
        log.info("# Server           = "+ InetAddress.getLocalHost().getHostAddress());
        log.info("# Host           	= "+ request.getRemoteHost());
        log.info("# RequestURI     	= "+ httpRequest.getRequestURI());
        log.info("########################################\n");

        return HandlerInterceptor.super.preHandle(request, response, object);

    }

    /*@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("==================== END ======================");
        log.info("===============================================");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }*/

}
