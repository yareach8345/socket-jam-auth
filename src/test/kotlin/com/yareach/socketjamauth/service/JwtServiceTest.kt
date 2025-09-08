package com.yareach.socketjamauth.service

import com.yareach.socketjamcommon.utils.KeyConverter
import org.junit.jupiter.api.DisplayName
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.Base64
import kotlin.test.Test
import kotlin.test.assertEquals

class JwtServiceTest {
    val keyConverter = KeyConverter()

    val testPublicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoZ5rXAQrTd9gt3qhkvMH03QgEr18KicVWsMN/py1XA84DEqFlvispaJ5HA1oRXJ950xBrJB7JS9LzesqMFV5Y5ZgVTSYRE8qDPfGwcw0e5cMGmNs4+ICLV42DsFvhZz3T161TWxIIUFoQEUAKXx9hdhgHsnDwj31/Ro6DgJTE4XVjv7klFluWIln3IwgW47cZEz0BIsyCCz5bC7Q8WmUDP/gtprFDFZwY27dfrUsPSbsJhMAFBlspXp40nwqIWar+9lDyDi4mNW9QsNo7mGNFnmmZkjYf4++GhslypESYx1vmlXZIsEgjZ6LBYWHBZ6XDfyYZb+A19355DgUj82pDQIDAQAB"

    val testPublicKey = keyConverter.stringToPublicKey(testPublicKeyString)

    val jwtService = KeyService(keyConverter, testPublicKey)

    @Test
    @DisplayName("public key 공유를 위한 jwks 생성")
    fun jwksTest() {
        val jwks = jwtService.getJwks()

        val nBytes = Base64.getUrlDecoder().decode(jwks.n)
        val eBytes = Base64.getUrlDecoder().decode(jwks.e)

        val modulus = java.math.BigInteger(1, nBytes)
        val exponent = java.math.BigInteger(1, eBytes)

        val spec = RSAPublicKeySpec(modulus, exponent)
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.generatePublic(spec)

        assertEquals(testPublicKey, publicKey)
    }
}