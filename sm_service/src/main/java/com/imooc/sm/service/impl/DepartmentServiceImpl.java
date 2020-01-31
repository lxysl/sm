package com.imooc.sm.service.impl;

import com.imooc.sm.dao.DepartmentDao;
import com.imooc.sm.entity.Department;
import com.imooc.sm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    public void add(Department department) {
        this.departmentDao.insert(department);
    }

    public void remove(Integer id) {
        this.departmentDao.delete(id);
    }

    public void edit(Department department) {
        this.departmentDao.update(department);
    }

    public Department get(Integer id) {
        return this.departmentDao.selectById(id);
    }

    public List<Department> getAll() {
        return this.departmentDao.selectAll();
    }
}
