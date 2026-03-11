package sparta.cloudassignment.global.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect // 1. "나는 스프링클러(AOP 모듈)입니다!" 선언
@Component // 2. 스프링 컨테이너에 빈으로 등록
public class ApiLoggingAspect {

    @Autowired
    private ObjectMapper objectMapper;

    // 3. Pointcut: "정확히 어느 화분에 물을 줄 건데?"
    @Around("@annotation(Logging)")
    public Object logApi(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes()).getRequest();
        // 요청 HTTP 메서드
        String method = request.getMethod();
        // 요청 url
        String requestURL = request.getRequestURI();
        // 요청 IP 주소
        String ip = request.getRemoteAddr();
        // 요청 컨트롤러 메서드
        String controllerMethod = joinPoint.getSignature().toShortString();
        // 요청 파라미터
        Object[] params = joinPoint.getArgs();

        log.info("""
                
                [API - LOG] {} {} | Controller: {} | IP: {} | Params: {}
                
                """,
                method,
                requestURL,
                controllerMethod,
                ip,
                Arrays.toString(params)
        );

//        long start = System.currentTimeMillis();
        try{
            return joinPoint.proceed();
        }catch (Exception e){
            log.error("""
                
                [API - ERROR] {} {} | Controller: {}| message: {}
                
                """,
                method,
                requestURL,
                controllerMethod,
                e.getMessage(),
                e); // stacktrace 자동 출력

            throw e;
        }
//        long end = System.currentTimeMillis();


    }
}