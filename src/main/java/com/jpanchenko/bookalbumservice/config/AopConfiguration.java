package com.jpanchenko.bookalbumservice.config;


import com.jpanchenko.bookalbumservice.metrics.ResponseTimeInterceptor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class AopConfiguration {

    @Pointcut("execution(public * org.springframework.web.client.RestOperations.*(..))")
    public void monitor() {
        // pointcut method should be empty
    }

    @Bean
    public ResponseTimeInterceptor responseTimeInterceptor() {
        return new ResponseTimeInterceptor();
    }

    @Bean
    public Advisor performanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.jpanchenko.bookalbumservice.config.AopConfiguration.monitor()");
        return new DefaultPointcutAdvisor(pointcut, responseTimeInterceptor());
    }
}
