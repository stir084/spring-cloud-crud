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

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }
    fun authenticate(username: String, password: String): Boolean {
        // UserRepository를 사용하여 사용자 정보와 비밀번호를 확인하고 인증 로직을 구현합니다.
        val user = userRepository.findByUsername(username)
        if (user != null) {
            println(passwordEncoder.matches(password, user.password))
        }
        return user != null && passwordEncoder.matches(password, user.password)
    }

    // 로그인 등 다른 필요한 메서드 추가
}