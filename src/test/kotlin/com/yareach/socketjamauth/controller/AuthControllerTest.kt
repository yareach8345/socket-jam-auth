package com.yareach.socketjamauth.controller

import com.yareach.socketjamauth.dto.auth.TokenRequestDto
import com.yareach.socketjamcommon.domain.security.JwtTokenEncoder
import com.yareach.socketjamcommon.utils.KeyConverter
import com.yareach.socketjamcommon.vo.user.UserVo
import io.jsonwebtoken.Jwts
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import java.security.interfaces.RSAPublicKey
import java.util.UUID
import kotlin.jvm.java
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.text.split
import kotlin.toString

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest @Autowired constructor (
    jwtTokenEncoder: JwtTokenEncoder,
    private val keyConverter: KeyConverter,
    private val webTestClient: WebTestClient,
    private val publicKey: RSAPublicKey,
) {
    val testUser = UserVo(
        UUID.randomUUID(),
        "testUser"
    )
    val testUserToken = jwtTokenEncoder.createJwt(testUser.nickName, testUser.userId)

    @Test
    @DisplayName("Token 생성 테스트")
    fun generateToken() {
        val body = TokenRequestDto("testUser")

        val result = webTestClient.post()
            .uri("/api/v1/token")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .exchange()
            .expectStatus().isOk
            .expectHeader().exists("Authorization")
            .expectHeader().valueMatches("Authorization", "^Bearer .*")
            .expectBody(String::class.java).isEqualTo("success")
            .returnResult()

        val auth = result.responseHeaders["Authorization"]!![0]

        val token = auth.split(" ")[1]
        val payload = Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token).payload

        assertNotNull(payload["nickName"])
        assertEquals("testUser", payload["nickName"])

        assertNotNull(payload["userId"])
        assertInstanceOf(String::class.java, payload["userId"])
        assertEquals(payload["userId"], UUID.fromString(payload["userId"].toString()).toString())
    }

    @Test
    @DisplayName("USER가 올바른 Auth Header를 가지고 check를 할 경우 성공")
    fun checkAuthSuccessWithUserRole() {
        webTestClient.get()
            .uri("/api/v1/check")
            .header("Authorization", "Bearer $testUserToken")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.isAuth").isEqualTo(true)
            .jsonPath("$.detail").isNotEmpty
            .jsonPath("$.detail.userId").isEqualTo(testUser.userId.toString())
            .jsonPath("$.detail.nickName").isEqualTo(testUser.nickName)
            .jsonPath("$.detail.role").isArray()
            .jsonPath("$.detail.role").isNotEmpty()
            .jsonPath("$.detail.role.length()").isEqualTo(1)
            .jsonPath("$.detail.role[0]").isEqualTo("USER")
    }

    @Test
    @DisplayName("Token을 가지고 있지 않으면 실패")
    fun checkAuthFailure() {
        webTestClient.get()
            .uri("/api/v1/check")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.isAuth").isEqualTo(false)
            .jsonPath("$.detail").isEmpty()
    }
}