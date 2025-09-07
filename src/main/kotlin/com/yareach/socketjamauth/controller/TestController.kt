package com.yareach.socketjamauth.controller

import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Profile("test", "dev")
class TestController {
    @GetMapping("/test")
    fun test(): String {
        return "hello from auth server"
    }
}