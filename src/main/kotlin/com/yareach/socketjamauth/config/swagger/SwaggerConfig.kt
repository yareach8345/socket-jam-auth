package com.yareach.socketjamauth.config.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Socket jam 인증서버")
                .version("1.0")
                .description("Socket jam 프로젝트의 서버 중 하나 토큰 생성과 key 관리를 담당")
        )
}