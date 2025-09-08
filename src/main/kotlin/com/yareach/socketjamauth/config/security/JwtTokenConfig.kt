package com.yareach.socketjamauth.config.security

import com.yareach.socketjamcommon.domain.security.JwtTokenDecoder
import com.yareach.socketjamcommon.domain.security.JwtTokenEncoder
import com.yareach.socketjamcommon.utils.KeyConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
class JwtTokenConfig(
    private val keyConverter: KeyConverter,
) {
    @Bean
    fun jwtPublicKey(
        @Value("\${spring.security.public-key}") key: String,
    ) = keyConverter.stringToPublicKey(key)

    @Bean
    fun jwtPrivateKey(
        @Value("\${spring.security.private-key}") key: String,
    ) = keyConverter.stringToPrivateKey(key)

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
