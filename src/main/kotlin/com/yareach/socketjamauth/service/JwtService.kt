package com.yareach.socketjamauth.service

import com.yareach.socketjamauth.dto.jwt.JwksDto
import org.springframework.stereotype.Service
import java.security.interfaces.RSAPublicKey
import java.util.Base64

@Service
class JwtService(
    publicKey: RSAPublicKey,
) {
    val jwksDto = JwksDto(
        kty = "RSA",
        e = Base64.getEncoder().encodeToString(publicKey.publicExponent.toByteArray()),
        n = Base64.getEncoder().encodeToString(publicKey.modulus.toByteArray()),
        alg = "RS256",
        use = "sig",
        kid = "main-key"
    )

    fun getJwks(): JwksDto {
        return jwksDto
    }
}