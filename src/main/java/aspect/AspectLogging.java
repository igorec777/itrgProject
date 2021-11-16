package aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
@Configurable
public class AspectLogging {


    //Pointcuts for services:

    //log result for find* methods
    @Pointcut("execution(public * service.impl.*.find*(..))")
    void logFindMethodsSuccessResult() {
    }

    //log ecxeptions for all methods
    @Pointcut("execution(* service.impl..*(..))")
    void logMethodsExceptions() {
    }

    @AfterReturning(value = "logFindMethodsSuccessResult()", returning = "retVal")
    void logResult(JoinPoint joinPoint, Object retVal) {
        log.warn("Method: " + joinPoint.getSignature().getName() + parseArgsToStr(joinPoint.getArgs())
                + " | Returns: " + retVal);
    }

    @Around("logMethodsExceptions()")
    Object logExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("Method: " + joinPoint.getSignature().getName() + parseArgsToStr(joinPoint.getArgs()) +
                    " | Message: " + ex.getMessage());
            //ex.printStackTrace();
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
