package com.yareach.socketjamauth.config.security

import com.yareach.socketjamcommon.domain.security.JwtTokenDecoder
import com.yareach.socketjamcommon.domain.security.JwtTokenEncoder
import com.yareach.socketjamcommon.util.JwtUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.interfaces.RSAPublicKey

@Configuration
class JwtTokenConfig {
    @Bean
    fun jwtPublicKey(
        @Value("\${spring.jwt.public-key}") key: String,
    ) = JwtUtil.stringToPublicKey(key) as RSAPublicKey

    @Bean
    fun jwtTokenEncoder(
        @Value("\${spring.jwt.private-key}") privateKey: String,
    ): JwtTokenEncoder {
        return JwtTokenEncoder.fromPrivateKey(privateKey)
    }

    @Bean
    fun jwtTokenDecoder(
        @Value("\${spring.jwt.public-key}") publicKey: String,
    ): JwtTokenDecoder {
        return JwtTokenDecoder.fromPublicKey(publicKey)
    }
}
