package com.yareach.socketjamauth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Profile("test", "dev")
@RestController
@RequestMapping("/test")
@Tag(name = "테스트 컨트롤러", description = "서버 상태를 확인하기 위한 엔드포인트 <br>dev, test 프로파일 시에만 활성화")
class TestController {
    @GetMapping
    @Operation(summary = "테스트", description = "연결확인을 위해 임시로 만든 테스트 엔드포인트")
    fun test(): String {
        return "hello from auth server"
    }
}