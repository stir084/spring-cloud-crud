package com.example.microserviceuser.dto

class UserDto {
    data class RegisterRequest(val username: String, val password: String)
    data class UserResponse(val id: Long?, val username: String)
    data class LoginRequest(val username: String, val password: String)
    data class LoginResponse(val token: String)
}