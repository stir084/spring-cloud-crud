package com.example.gatewayserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
@Configuration
class WebConfig : WebFluxConfigurer { // 이 클래스 필요 없으니 추후 삭제
    @Value("\${DEPLOY_HOST:127.0.0.1}")
    private val deployHost: String = ""

    @Value("\${DEPLOY_PORT:80}")
    private val deployPort: String = ""

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "$deployHost:$deployPort", deployHost)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("Authorization", "content-type")
            .exposedHeaders("Authorization")
            .maxAge(3600)
    }
}