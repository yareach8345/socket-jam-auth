package com.yareach.socketjamauth.dto.auth

data class AuthDetail(
    val userId: String,
    val nickName: String,
    val role: List<String>
)
