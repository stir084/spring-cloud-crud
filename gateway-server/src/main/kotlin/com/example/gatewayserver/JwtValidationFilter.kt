package com.example.gatewayserver

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtValidationFilter(private val jwtTokenProvider: JwtTokenProvider) : GatewayFilter {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        // JWT 토큰 검사 로직을 구현합니다.
        val token = extractJwtTokenFromRequest(request)
        val isValid = jwtTokenProvider.validateToken(token)

        if (!isValid) {
            return unauthorizedResponse(exchange)
        }

        // JWT 토큰이 유효한 경우 요청을 그대로 전달합니다.
        return chain.filter(exchange)
    }

    private fun extractJwtTokenFromRequest(request: ServerHttpRequest): String {
        // 요청에서 JWT 토큰을 추출하는 로직을 구현합니다.
        // 예를 들어, "Authorization" 헤더에서 토큰을 추출할 수 있습니다.
        val tokenHeader = request.headers.getFirst("Authorization") ?: ""
        return if (tokenHeader.startsWith("Bearer ")) {
            tokenHeader.substring(7)
        } else {
            ""
        }
    }

    private fun unauthorizedResponse(exchange: ServerWebExchange): Mono<Void> {
        val response = exchange.response
        response.statusCode = HttpStatus.UNAUTHORIZED
        return response.setComplete()
    }
}