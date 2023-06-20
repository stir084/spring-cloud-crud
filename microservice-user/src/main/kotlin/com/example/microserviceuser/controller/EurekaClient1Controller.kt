package com.example.eurekaclient1.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EurekaClient1Controller {
    @GetMapping("/user/hello")
    fun eurekaclient(): String {
        return "user"
    }
}