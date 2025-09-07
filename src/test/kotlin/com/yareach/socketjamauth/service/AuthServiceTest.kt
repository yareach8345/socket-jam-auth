package com.yareach.socketjamauth.service

import com.yareach.socketjamcommon.domain.security.JwtTokenEncoder
import com.yareach.socketjamcommon.util.JwtUtil
import io.jsonwebtoken.Jwts
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.DisplayName
import java.util.UUID
import javax.crypto.SecretKey
import kotlin.jvm.java
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.toString

class AuthServiceTest {
    val jwtUtil = JwtUtil()

    val secretString: String = "a-string-secret-256-bits-long-for-test"

    val secretKey: SecretKey = jwtUtil.stringToSecretKey(secretString)

    val authService = AuthService(JwtTokenEncoder.fromSecretKey(secretKey))

    @Test
    @DisplayName("토큰 생성 테스트")
    fun generateTokenTest() = runTest {
        val token = authService.generateToken("testUser")

        val payload = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload

        assertNotNull(payload["nickName"])
        assertEquals("testUser", payload["nickName"])

        assertNotNull(payload["userId"])
        assertInstanceOf(String::class.java, payload["userId"])
        //적법한 UUID인지 검사. 적법하지 않다면 fromString과정에서 IllegalArgumentException에러 발생
        assertEquals(payload["userId"], UUID.fromString(payload["userId"].toString()).toString())
    }
}