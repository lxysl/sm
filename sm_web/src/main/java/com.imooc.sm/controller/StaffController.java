package com.imooc.sm.controller;

import com.imooc.sm.entity.Department;
import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.DepartmentService;
import com.imooc.sm.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller("staffController")
public class StaffController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private DepartmentService departmentService;

    /**
     * 查看所有员工
     * 控制器：/staff/list.do
     * 页面：/staff_list.jsp
     */
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Staff> list = this.staffService.getAll();
        request.setAttribute("LIST", list);
        request.getRequestDispatcher("../staff_list.jsp").forward(request, response);
    }

    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> list = this.departmentService.getAll();
        request.setAttribute("DLIST", list);
        request.getRequestDispatcher("../staff_add.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String account = (String) request.getParameter("account");
        String name = (String) request.getParameter("name");
        String sex = (String) request.getParameter("sex");
        String idNumber = (String) request.getParameter("idNumber");
        String info = (String) request.getParameter("info");
        Date bornDate = null;
        try {
            //String转Date
            bornDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("bornDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer did = Integer.parseInt(request.getParameter("did"));

        Staff staff = new Staff(account, did, name, sex, idNumber, bornDate, info);
        this.staffService.add(staff);
        response.sendRedirect("list.do");
    }

    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Staff staff = this.staffService.get(id);
        request.setAttribute("OBJ", staff);
        List<Department> list = this.departmentService.getAll();
        request.setAttribute("DLIST", list);
        request.getRequestDispatcher("../staff_edit.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String account = (String) request.getParameter("account");
        String name = (String) request.getParameter("name");
        String sex = (String) request.getParameter("sex");
        String idNumber = (String) request.getParameter("idNumber");
        String info = (String) request.getParameter("info");
        Date bornDate = null;
        try {
            //String转Date
            bornDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("bornDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer did = Integer.parseInt(request.getParameter("did"));

        Staff staff = this.staffService.get(id);
        staff.setAccount(account);
        staff.setName(name);
        staff.setSex(sex);
        staff.setIdNumber(idNumber);
        staff.setInfo(info);
        staff.setBornDate(bornDate);
        staff.setDid(did);
        Department department = this.departmentService.get(did);
        staff.setDepartment(department);

        this.staffService.edit(staff);
        //判断修改用户是否为当前登录用户，如果相同则把修改后的用户放入Session中
//        Staff staff1 = (Staff) request.getSession().getAttribute("USER");
//        if (staff1.getId().equals(staff.getId())) {
//            request.getSession().setAttribute("USER", staff);
//        }
        response.sendRedirect("list.do");
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        this.staffService.remove(id);
        response.sendRedirect("list.do");
    }

    public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Staff staff = this.staffService.get(id);
        request.setAttribute("OBJ", staff);
        request.getRequestDispatcher("../staff_detail.jsp").forward(request, response);
    }
}
