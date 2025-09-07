package com.yareach.socketjamauth.service

import com.yareach.socketjamcommon.domain.security.JwtTokenEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthService(
    private val jwtTokenEncoder: JwtTokenEncoder,
) {
    suspend fun generateToken(username: String): String {

        return jwtTokenEncoder.createJwt(username, UUID.randomUUID())
    }
}