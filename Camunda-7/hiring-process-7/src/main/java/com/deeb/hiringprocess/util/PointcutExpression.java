package com.deeb.hiringprocess.util;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutExpression {
    @Pointcut("execution(* com.deeb.hiringprocess..*.*(..))")
    public void all() {
    }
}

