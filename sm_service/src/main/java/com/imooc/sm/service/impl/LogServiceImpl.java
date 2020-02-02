package com.imooc.sm.service.impl;

import com.imooc.sm.dao.LogDao;
import com.imooc.sm.entity.Log;
import com.imooc.sm.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("logService")
public class LogServiceImpl implements LogService {
    private final LogDao logDao;

    @Autowired
    public LogServiceImpl(LogDao logDao) {
        this.logDao = logDao;
    }

    public void addSystemLog(Log log) {
        log.setOprTime(new Date());
        log.setType("system");
        this.logDao.insert(log);
    }

    public void addLoginLog(Log log) {
        log.setOprTime(new Date());
        log.setType("login");
        this.logDao.insert(log);
    }

    public void addOperationLog(Log log) {
        log.setOprTime(new Date());
        log.setType("operation");
        this.logDao.insert(log);
    }

    public List<Log> getSystemLog() {
        return this.logDao.selectByType("system");
    }

    public List<Log> getLoginLog() {
        return this.logDao.selectByType("login");
    }

    public List<Log> getOperationLog() {
        return this.logDao.selectByType("operation");
    }
}
