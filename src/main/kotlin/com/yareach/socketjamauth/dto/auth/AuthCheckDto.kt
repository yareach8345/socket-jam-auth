package com.yareach.socketjamauth.dto.auth

data class AuthCheckDto(
    val isAuth: Boolean,
    val detail: AuthDetail? = null
)