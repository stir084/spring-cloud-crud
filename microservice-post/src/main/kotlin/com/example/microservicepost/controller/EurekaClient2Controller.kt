package com.example.eurekaclient2.controller

import com.example.microservicepost.FeignClientInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EurekaClient2Controller(
    @Autowired private val feignClientInterface: FeignClientInterface
){
    @GetMapping("/post/hello")
    fun eurekaclient(): String {
        return "post"
    }

    @GetMapping("/post/test")
    fun test(): String {
        println(feignClientInterface.eurekaclient())
        return "test"
    }
}