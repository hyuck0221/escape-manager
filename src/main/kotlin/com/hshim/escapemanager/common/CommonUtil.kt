package com.hshim.escapemanager.common

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature

object CommonUtil {
    fun <T : Annotation> ProceedingJoinPoint.getAnnotation(annotationClass: Class<T>): T {
        return (this.signature as MethodSignature).method.getAnnotation(annotationClass)
    }
}