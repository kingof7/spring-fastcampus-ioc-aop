package com.example.aop.aop;

import com.example.aop.dto.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Aspect
@Component
public class DecodeAop {

    @Pointcut("execution(* com.example.aop.controller..*.*(..))")
    private void cut(){}

    @Pointcut("@annotation(com.example.aop.annotation.Decode)") // timer 제약
    private void enableDecode(){} // logging 처리

    @Before("cut() && enableDecode()")
    public void before(JoinPoint joinPoint) throws UnsupportedEncodingException {
        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if(arg instanceof User){ // User 클래스로 매칭된다면,
                User user = User.class.cast(arg); // type casting
                String base64Email = user.getEmail();
                String email = new String(Base64.getDecoder().decode(base64Email), "UTF-8"); // 반화타입이 byte[]라서 new String으로 감싸줌
                user.setEmail(email);
            }
        }
    }

    @AfterReturning(value = "cut() && enableDecode()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj){
        if(returnObj instanceof User){
            User user = User.class.cast(returnObj); // type casting
            String email = user.getEmail();
            String base64Email = Base64.getEncoder().encodeToString(email.getBytes()); // encoding
            user.setEmail(email);
        }
    }
}
