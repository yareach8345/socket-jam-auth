package com.yareach.socketjamauth.dto.auth

data class AuthCheckDto(
    val isAuthenticated: Boolean,
    val detail: AuthDetail? = null
)