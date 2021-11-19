package aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
@Configurable
public class AspectLogging {

    //@Autowired
    //private Marker marker;

    //Pointcuts for services:

    //log result
    @Pointcut("execution(!void service.impl..*(..))")
    void logMethodsSuccessResult() {
    }

    //log execution
    @Pointcut("execution(void service.impl..*(..))")
    void logMethodsSuccessExecution() {
    }

    //log exceptions
    @Pointcut("execution(* service.impl..*(..))")
    void logMethodsExceptions() {
    }

    @After("logMethodsSuccessExecution()")
    void logSuccessExecution(JoinPoint joinPoint) {
        log.warn("Executed Method: " + joinPoint.getSignature().getName() + parseArgsToStr(joinPoint.getArgs()));
    }

    @AfterReturning(value = "logMethodsSuccessResult()", returning = "retValue")
    void logResult(JoinPoint joinPoint, Object retValue) {
        log.warn("Executed Method: " + joinPoint.getSignature().getName() + parseArgsToStr(joinPoint.getArgs()) +
                " | Returns: " + retValue);
    }

    @Around("logMethodsExceptions()")
    Object logExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("Method: " + joinPoint.getSignature().getName() + parseArgsToStr(joinPoint.getArgs()) +
                    " | Message: " + ex.getMessage());
            throw ex;
        }
    }

    private StringBuilder parseArgsToStr(Object[] args) {
        StringBuilder argsStr = new StringBuilder();
        argsStr.append("(");
        for (int i = 0; i < args.length; i++) {
            argsStr.append(args[i]);
            if (i != args.length - 1) {
                argsStr.append(",");
            }
        }
        argsStr.append(")");
        return argsStr;
    }
}
