package com.imooc.sm.controller;

import com.imooc.sm.entity.Log;
import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller("logController")
public class LogController {
    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    public void operationLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Log> list = this.logService.getOperationLog();
        request.setAttribute("LIST", list);
        request.setAttribute("TYPE", "操作");
        request.getRequestDispatcher("../log_list.jsp").forward(request, response);
    }

    public void loginLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Log> list = this.logService.getLoginLog();
        request.setAttribute("LIST", list);
        request.setAttribute("TYPE", "登录");
        request.getRequestDispatcher("../log_list.jsp").forward(request, response);
    }

    public void systemLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Log> list = this.logService.getSystemLog();
        request.setAttribute("LIST", list);
        request.setAttribute("TYPE", "系统");
        request.getRequestDispatcher("../log_list.jsp").forward(request, response);
    }
}
