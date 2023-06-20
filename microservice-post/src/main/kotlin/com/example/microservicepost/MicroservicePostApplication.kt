package com.example.microservicepost

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MicroservicePostApplication

fun main(args: Array<String>) {
	runApplication<MicroservicePostApplication>(*args)
}
