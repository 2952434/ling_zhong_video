//package com.lingzhong.video.config;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class CorsFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        String orignalHeader = StringUtils.defaultIfBlank(request.getHeader("Origin"), "*");
//        // 指定本次预检请求的有效期
//        response.setHeader("Access-Control-Max-Age", "3600");
//        // 服务器支持的所有头信息字段
//        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
//        response.setHeader("Access-Control-Allow-Origin", orignalHeader);
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        filterChain.doFilter(request, response);
//    }
//}
