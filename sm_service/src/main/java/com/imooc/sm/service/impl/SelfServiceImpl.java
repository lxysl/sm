package com.imooc.sm.service.impl;

import com.imooc.sm.dao.SelfDao;
import com.imooc.sm.dao.StaffDao;
import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.SelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("selfServiceImpl")
public class SelfServiceImpl implements SelfService {

    private final SelfDao selfDao;
    private final StaffDao staffDao;

    @Autowired
    public SelfServiceImpl(SelfDao selfDao, StaffDao staffDao) {
        this.selfDao = selfDao;
        this.staffDao = staffDao;
    }

    public Staff login(String account, String password) {
        Staff staff = this.selfDao.selectByAccount(account);
        if (staff == null) {
            return null;
        } else if (staff.getPassword().equals(password)) {
            return staff;
        }
        return null;
    }

    public void changePassword(Integer id, String password) {
        Staff staff = this.staffDao.selectById(id);
        staff.setPassword(password);
        this.staffDao.update(staff);
    }
}
