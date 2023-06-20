package com.example.microservicepost

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "user")
interface FeignClientInterface {
    @GetMapping("/user/hello")
    fun eurekaclient(): String
}