package com.example.aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect // param
@Component // spring이 관리
public class ParameterAop {
    //pointcut : 어디에 적용 시킬건지, 컨트롤러 함수 내에 로거작성안해도 포인트컷(외부)에서 바라볼 수 있음
    @Pointcut("execution(* com.example.aop.controller..*.*(..))") // 패키지하위에 모든 메서드를 다 aop로 보낸다
    private void cut() {
        
    }

    @Before("cut()") // (pointcut) cut()이 실행되는 순간
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName());
        Object[] args = joinPoint.getArgs();

        for(Object obj : args){
            System.out.println("type : " + obj.getClass().getSimpleName());
            System.out.println("value : " + obj);

        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj") // before성공시 반환
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {
        System.out.print("return obj : ");
        System.out.println(returnObj); // echo의 형태로 동작
    }
}
