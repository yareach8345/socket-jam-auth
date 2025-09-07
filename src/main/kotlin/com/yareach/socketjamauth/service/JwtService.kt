package com.yareach.socketjamauth.service

import com.yareach.socketjamcommon.extension.toJwksDto
import com.yareach.socketjamcommon.util.JwtUtil
import org.springframework.stereotype.Service
import java.security.interfaces.RSAPublicKey

@Service
class JwtService(
    jwtUtil: JwtUtil,
    publicKey: RSAPublicKey,
) {
    val jwkVo = jwtUtil.publicKeyToJwk(publicKey)

    fun getJwks() = jwkVo.toJwksDto()
}