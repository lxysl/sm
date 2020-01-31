package com.imooc.sm.controller;

import com.imooc.sm.entity.Department;
import com.imooc.sm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller("departmentController")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 查看所有部门
     * 控制器：/department/list.do
     * 页面：/department_list.jsp
     */
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> list = departmentService.getAll();
        request.setAttribute("LIST", list);
        request.getRequestDispatcher("../department_list.jsp").forward(request, response);
    }

    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("../department_add.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = (String) request.getParameter("name");
        String address = (String) request.getParameter("address");
        Department department = new Department(name, address);
        this.departmentService.add(department);
        response.sendRedirect("list.do");
    }

    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Department department = this.departmentService.get(id);
        request.setAttribute("OBJ", department);
        request.getRequestDispatcher("../department_edit.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = (String) request.getParameter("name");
        String address = (String) request.getParameter("address");
        Department department = new Department(name, address);
        department.setId(id);

        this.departmentService.edit(department);
        response.sendRedirect("list.do");
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        this.departmentService.remove(id);
        response.sendRedirect("list.do");
    }
}
