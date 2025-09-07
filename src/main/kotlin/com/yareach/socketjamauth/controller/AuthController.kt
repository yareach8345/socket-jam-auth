package com.yareach.socketjamauth.controller

import com.yareach.socketjamauth.dto.auth.AuthCheckDto
import com.yareach.socketjamauth.dto.auth.AuthDetail
import com.yareach.socketjamauth.dto.auth.TokenRequestDto
import com.yareach.socketjamauth.service.AuthService
import com.yareach.socketjamcommon.config.security.CustomUserDetail
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/token")
    suspend fun getToken(
        @RequestBody dto: TokenRequestDto
    ): ResponseEntity<String> {
        try {
            val token = authService.generateToken(dto.userName)

            return ResponseEntity.ok()
                .header("Authorization", "Bearer $token")
                .body("success")
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/check")
    suspend fun checkAuth(
        @AuthenticationPrincipal user: CustomUserDetail?
    ): ResponseEntity<AuthCheckDto> = when(user) {
        null -> ResponseEntity.ok(
            AuthCheckDto(false) )
        else -> ResponseEntity.ok(
            AuthCheckDto(
                true,
                AuthDetail(user.userId, user.nickName, user.role)
            )
        )
    }
}