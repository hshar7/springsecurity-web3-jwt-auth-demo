package com.example.demo.security

import org.springframework.security.core.annotation.AuthenticationPrincipal

import java.lang.annotation.*

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@AuthenticationPrincipal
annotation class CurrentUser
