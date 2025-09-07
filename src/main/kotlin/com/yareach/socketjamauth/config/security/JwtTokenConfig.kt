package com.yareach.socketjamauth.config.security

import com.yareach.socketjamcommon.domain.security.JwtTokenDecoder
import com.yareach.socketjamcommon.domain.security.JwtTokenEncoder
import com.yareach.socketjamcommon.util.JwtUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
class JwtTokenConfig(
    private val jwtUtil: JwtUtil,
) {
    @Bean
    fun jwtPublicKey(
        @Value("\${spring.jwt.public-key}") key: String,
    ) = jwtUtil.stringToPublicKey(key)

    @Bean
    fun jwtPrivateKey(
        @Value("\${spring.jwt.private-key}") key: String,
    ) = jwtUtil.stringToPrivateKey(key)

    @Bean
    fun jwtTokenEncoder(
        privateKey: RSAPrivateKey
    ): JwtTokenEncoder {
        return JwtTokenEncoder.fromPrivateKey(privateKey)
    }

    @Bean
    fun jwtTokenDecoder(
        publicKey: RSAPublicKey
    ): JwtTokenDecoder {
        return JwtTokenDecoder.fromPublicKey(publicKey)
    }
}
