package com.imooc.sm.dao;

import com.imooc.sm.entity.Staff;

import java.util.List;

public interface StaffDao {
    void insert(Staff staff);

    void delete(Integer id);

    void update(Staff staff);

    Staff selectById(Integer id);

    List<Staff> selectAll();
}
