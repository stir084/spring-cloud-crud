package com.example.microserviceuser

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "post")
interface FeignClientInterface {
    @GetMapping("/posts/mypost-count")
    fun getMyPostCount(@RequestHeader("Authorization") authorization: String): Long
}