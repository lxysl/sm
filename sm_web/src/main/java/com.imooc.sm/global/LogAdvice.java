package com.imooc.sm.global;

import com.imooc.sm.entity.Log;
import com.imooc.sm.entity.Staff;
import com.imooc.sm.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Aspect
public class LogAdvice {
    private final LogService logService;

    @Autowired
    public LogAdvice(LogService logService) {
        this.logService = logService;
    }

    @After("execution(* com.imooc.sm.controller.*.*(..)) && " +
            "!execution(* com.imooc.sm.controller.SelfController.*(..)) && " +
            "!execution(* com.imooc.sm.controller.*.to*(..))")
    public void operationLog(JoinPoint joinPoint) {
        Log log = new Log();
        //获取操作类名
        log.setModule(joinPoint.getTarget().getClass().getSimpleName());
        //获取操作方法
        log.setOperation(joinPoint.getSignature().getName());
        //从方法参数列表中获取request对象
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("USER");
        log.setOperator(staff.getAccount());
        log.setResult("成功");
        this.logService.addOperationLog(log);
    }

    @AfterThrowing(throwing = "e",
            pointcut = "execution(* com.imooc.sm.controller.*.*(..)) && " +
                    "!execution(* com.imooc.sm.controller.SelfController.*(..))")
    public void systemLog(JoinPoint joinPoint, Throwable e) {
        Log log = new Log();
        //获取操作类名
        log.setModule(joinPoint.getTarget().getClass().getSimpleName());
        //获取操作方法
        log.setOperation(joinPoint.getSignature().getName());
        //从方法参数列表中获取request对象
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("USER");
        log.setOperator(staff.getAccount());
        log.setResult(e.getClass().getSimpleName());
        this.logService.addSystemLog(log);
    }

    private void log(JoinPoint joinPoint) {
        Log log = new Log();
        //获取操作类名
        log.setModule(joinPoint.getTarget().getClass().getSimpleName());
        //获取操作方法
        log.setOperation(joinPoint.getSignature().getName());
        //从方法参数列表中获取request对象
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("USER");
        if (obj == null) {
            log.setOperator(request.getParameter("account"));
            log.setResult("失败");
        } else {
            Staff staff = (Staff) obj;
            log.setOperator(staff.getAccount());
            log.setResult("成功");
        }
        this.logService.addLoginLog(log);
    }

    @After("execution(* com.imooc.sm.controller.SelfController.login(..))")
    public void loginLog(JoinPoint joinPoint) {
        this.log(joinPoint);
    }

    @Before("execution(* com.imooc.sm.controller.SelfController.logout(..))")
    public void logoutLog(JoinPoint joinPoint) {
        this.log(joinPoint);
    }
}
