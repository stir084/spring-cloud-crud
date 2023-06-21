package com.example.microserviceuser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class MicroserviceUserApplication

fun main(args: Array<String>) {
	runApplication<MicroserviceUserApplication>(*args)
}
