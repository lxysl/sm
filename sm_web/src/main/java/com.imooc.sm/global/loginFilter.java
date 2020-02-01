package com.imooc.sm.global;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class loginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();

        //去往登录
        if (path.toLowerCase().contains("login")) {
            filterChain.doFilter(request, response);
        } else {
            //去往其他页面
            HttpSession session = request.getSession();
            Object obj = session.getAttribute("USER");
            if (obj != null) {
                //已登录
                filterChain.doFilter(request, response);
            } else {
                //未登录
                response.sendRedirect(request.getContextPath() + "/toLogin.do");//同时考虑一级二级目录
            }
        }
    }

    @Override
    public void destroy() {

    }
}
