package com.example.microserviceuser.service

import com.example.microserviceuser.domain.User
import com.example.microserviceuser.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun registerUser(username: String, password: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(username = username, password = encodedPassword)
        return userRepository.save(user)
    }

    fun authenticate(username: String, password: String): Boolean {
        val user = userRepository.findByUsername(username)
        return user != null && passwordEncoder.matches(password, user.password)
    }
}