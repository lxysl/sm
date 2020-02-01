package com.imooc.sm.service.impl;

import com.imooc.sm.dao.StaffDao;
import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("staffService")
public class StaffServiceImpl implements StaffService {

    private final StaffDao staffDao;

    @Autowired
    public StaffServiceImpl(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public void add(Staff staff) {
        staff.setPassword("123456");
        staff.setWorkTime(new Date());
        staff.setStatus("正常");
        this.staffDao.insert(staff);
    }

    public void remove(Integer id) {
        this.staffDao.delete(id);
    }

    public void edit(Staff staff) {
        this.staffDao.update(staff);
    }

    public Staff get(Integer id) {
        return this.staffDao.selectById(id);
    }

    public List<Staff> getAll() {
        return this.staffDao.selectAll();
    }
}
