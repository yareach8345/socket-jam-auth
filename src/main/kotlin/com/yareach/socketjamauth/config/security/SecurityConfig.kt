package com.yareach.socketjamauth.config.security

import com.yareach.socketjambackend.config.security.filter.JwtFilter
import com.yareach.socketjamcommon.config.security.JwtAuthenticationConverter
import com.yareach.socketjamcommon.domain.security.JwtTokenDecoder
import com.yareach.socketjamcommon.domain.security.JwtTokenEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.UUID

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
            it
                .pathMatchers("/api/v1/rooms/search").permitAll()
                .pathMatchers("/api/v1/rooms/**").authenticated()
                .anyExchange().permitAll()
        }
        .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHORIZATION)
        .build()
}