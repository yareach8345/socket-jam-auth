package com.yareach.socketjamauth.service

import com.yareach.socketjamcommon.extensions.toJwksDto
import com.yareach.socketjamcommon.utils.KeyConverter
import org.springframework.stereotype.Service
import java.security.interfaces.RSAPublicKey

@Service
class KeyService(
    keyConverter: KeyConverter,
    publicKey: RSAPublicKey,
) {
    val jwkVo = keyConverter.publicKeyToJwk(publicKey)

    fun getJwks() = jwkVo.toJwksDto()
}