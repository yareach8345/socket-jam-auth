package com.yareach.socketjamauth.config.security

import com.yareach.socketjamcommon.domain.security.JwtTokenDecoder
import com.yareach.socketjamcommon.domain.security.JwtTokenEncoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtTokenConfig {
    @Bean
    fun jwtTokenEncoder(
        @Value("\${spring.jwt.private-key}") privateKey: String,
    ): JwtTokenEncoder {
        println("========================")
        println(privateKey)
        println("========================")
        return JwtTokenEncoder.fromPrivateKey(privateKey)
    }

    @Bean
    fun jwtTokenDecoder(
        @Value("\${spring.jwt.public-key}") publicKey: String,
    ): JwtTokenDecoder {
        println("========================")
        println(publicKey)
        println("========================")
        return JwtTokenDecoder.fromPublicKey(publicKey)
    }
}
