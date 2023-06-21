package com.example.microserviceuser.controller

import com.example.microserviceuser.config.JwtTokenProvider
import com.example.microserviceuser.config.TokenGenerator
import com.example.microserviceuser.dto.UserDto
import com.example.microserviceuser.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val userService: UserService,
                     private val tokenGenerator: TokenGenerator, private val jwtTokenProvider: JwtTokenProvider) {
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
            return ResponseEntity.ok(UserDto.LoginResponse("Bearer $token"))
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

    @GetMapping("/users/myinfo")
    fun getMyInfo(@RequestHeader("Authorization") authrization: String): ResponseEntity<UserDto.UserResponse> {
        val username = jwtTokenProvider.extractUsernameFromToken(authrization)
        val userResponse = userService.getMyInfo(authrization, username)
        return ResponseEntity.ok(userResponse)
    }
}


