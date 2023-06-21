package com.example.gatewayserver.config

import org.apache.http.HttpHeaders
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory
import org.springframework.stereotype.Component


@Component
class JwtValidationFilterFactory(private val jwtTokenProvider: JwtTokenProvider) :
    GatewayFilterFactory<JwtValidationFilterFactory.Config> {

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val authHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            val token = jwtTokenProvider.extractTokenFromHeader(authHeader)
            if (jwtTokenProvider.validateToken(token)) {
                val username = jwtTokenProvider.extractUsernameFromToken(token)
                request.mutate().header("username", username).build()
            }

            chain.filter(exchange)
        }
    }

    override fun getConfigClass(): Class<Config> {
        return Config::class.java
    }

    override fun newConfig(): Config {
        return Config()
    }

    data class Config(
        val allowedEndpoints: List<String> = emptyList()
    )
}