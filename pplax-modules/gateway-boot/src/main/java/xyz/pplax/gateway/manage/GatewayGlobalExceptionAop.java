//package xyz.pplax.gateway.manage;
//
// import lombok.extern.slf4j.Slf4j;
// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.annotation.AfterThrowing;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Before;
// import org.springframework.stereotype.Component;
// import xyz.pplax.core.util.LogUtils;
//
//
// @Slf4j
// @Component
// @Aspect
// public class GatewayGlobalExceptionAop {
//
//     public GatewayGlobalExceptionAop() {
//     }
//
//      @AfterThrowing(pointcut = "execution(public * *(..))", throwing = "e")
//      public void getDataFromRedis(Exception e) throws Throwable {
//          LogUtils.logExceptionInfo(e);
//      }
//
//      @AfterThrowing(value = "execution(* *(..))",throwing = "e")
//      public void log(JoinPoint jp, Exception e) {
//          System.out.println("执行异常通知");
//          System.out.println(e.getMessage());
//      }
//
//     @Before("execution(* *(..))")
//     public void beforeAdvice() {
//         System.out.println("Before advice called...");
//     }
// }
