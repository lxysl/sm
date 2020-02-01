package com.imooc.sm.dao;

import com.imooc.sm.entity.Staff;
import org.springframework.stereotype.Repository;

@Repository("SelfDao")
public interface SelfDao {
    Staff selectByAccount(String account);
}
