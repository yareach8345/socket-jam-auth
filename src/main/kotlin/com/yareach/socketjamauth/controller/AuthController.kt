package com.yareach.socketjamauth.controller

import com.yareach.socketjamauth.dto.auth.AuthCheckDto
import com.yareach.socketjamauth.dto.auth.AuthDetail
import com.yareach.socketjamauth.dto.auth.TokenRequestDto
import com.yareach.socketjamauth.service.AuthService
import com.yareach.socketjamcommon.config.security.CustomUserDetail
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@Tag(name = "인증 컨트롤러", description = "토큰 생성과 인증 체크를 위한 컨트롤러")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/token")
    @Operation(summary = "토큰 생성", description = "유저의 닉네임을 기반으로 토큰을 생성해 반환")
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
    @Operation(summary = "인증 확인", description = "Authentication 헤더를 기준으로 인증여부 확인")
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