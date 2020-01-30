package com.imooc.sm.global;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 核心控制器 解析URL获取对应的类和方法，据此调用对应的方法
 * 规范URL示例：
 * /staff/add.do
 * /login.do
 * 对应类与方法示例：
 * class staffController{
 * public void add（HttpServletRequest request,HttpServletResponse response）;
 * }
 */
public class DispatcherServlet extends GenericServlet {
    private ApplicationContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        context = new ClassPathXmlApplicationContext("spring.xml");
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //解析URL
        String path = request.getServletPath().substring(1); // staff/add.do或login.do
        int index = path.indexOf('/');
        String beanName = null;
        String methodName = null;
        if (index != -1) { // staff/add.do
            beanName = path.substring(0, index) + "Controller"; // staffController
            methodName = path.substring(index + 1, path.indexOf(".do")); // add
        } else { // login.do
            beanName = "selfController";
            methodName = path.substring(0, path.indexOf(".do")); // login
        }
        Object obj = context.getBean(beanName);
        try {
            Method method = obj.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(obj, request, response);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
