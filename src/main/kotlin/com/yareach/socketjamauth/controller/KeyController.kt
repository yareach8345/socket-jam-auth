package com.yareach.socketjamauth.controller

import com.yareach.socketjamauth.service.KeyService
import com.yareach.socketjamcommon.dto.JwksDto
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/.well-known/jwks.json")
class KeyController(
    private val keyService: KeyService,
) {

    @GetMapping("/public-key")
    suspend fun getPublicKey(): JwksDto {
        return keyService.getJwks()
    }
}