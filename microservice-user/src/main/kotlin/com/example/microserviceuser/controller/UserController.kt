package com.example.microserviceuser.controller

import com.example.microserviceuser.config.TokenGenerator
import com.example.microserviceuser.dto.UserDto
import com.example.microserviceuser.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService, private val tokenGenerator: TokenGenerator) {
    @PostMapping("/users/register")
    fun registerUser(@RequestBody registerRequest: UserDto.RegisterRequest): ResponseEntity<UserDto.UserResponse> {
        val user = userService.registerUser(registerRequest.username, registerRequest.password)
        val userResponse = UserDto.UserResponse(user.id, user.username)
        return ResponseEntity.ok(userResponse)
    }

    @PostMapping("/users/login")
    fun login(@RequestBody loginRequest: UserDto.LoginRequest): ResponseEntity<UserDto.LoginResponse> {
        // 사용자 인증 로직을 구현합니다.
        val isAuthenticated = userService.authenticate(loginRequest.username, loginRequest.password)
        if (isAuthenticated) {
            // 로그인 성공 시 JWT 토큰을 생성합니다.
            val token = tokenGenerator.generateToken(loginRequest.username)
            return ResponseEntity.ok(UserDto.LoginResponse(token))
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }
}