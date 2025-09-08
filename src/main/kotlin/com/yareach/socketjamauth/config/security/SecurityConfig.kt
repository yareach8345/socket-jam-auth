package com.yareach.socketjamauth.config.security

import com.yareach.socketjambackend.config.security.filter.JwtFilter
import com.yareach.socketjamcommon.config.security.JwtAuthenticationConverter
import com.yareach.socketjamcommon.domain.security.JwtTokenDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    jwtTokenDecoder: JwtTokenDecoder,
) {

    private val jwtFilter = JwtFilter(JwtAuthenticationConverter(jwtTokenDecoder))

    @Bean
    fun filter(http: ServerHttpSecurity): SecurityWebFilterChain = http
        .csrf{ it.disable() }
        .formLogin{ it.disable() }
        .httpBasic{ it.disable() }
        .logout{ it.disable() }
        .authorizeExchange{
            it.anyExchange().permitAll()
        }
        .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHORIZATION)
        .build()
}