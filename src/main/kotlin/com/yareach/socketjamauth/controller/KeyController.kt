package com.yareach.socketjamauth.controller

import com.yareach.socketjamauth.service.KeyService
import com.yareach.socketjamcommon.dto.JwksDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/.well-known/jwks.json")
@Tag(name = "Key 컨트롤러", description = "public key를 받기위한 컨트롤러")
class KeyController(
    private val keyService: KeyService,
) {

    @GetMapping
    @Operation(summary = "공개키", description = "JWT를 파싱하기 위한 공개키를 받아옴")
    suspend fun getPublicKey(): JwksDto {
        return keyService.getJwks()
    }
}