package com.yareach.socketjambackend.config.security.filter

import com.yareach.socketjamcommon.config.security.JwtAuthenticationConverter
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class JwtFilter(
    private val jwtAuthenticationConverter: JwtAuthenticationConverter
): WebFilter {
    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> = mono {
        val token = exchange.request.headers["Authorization"]
            ?.firstOrNull()
            ?.let { jwtAuthenticationConverter.convert(it) }
            ?: return@mono chain.filter(exchange).awaitSingleOrNull()

        chain.filter(exchange)
            .contextWrite{ ReactiveSecurityContextHolder.withAuthentication(token) }
            .awaitSingleOrNull()
    }
}