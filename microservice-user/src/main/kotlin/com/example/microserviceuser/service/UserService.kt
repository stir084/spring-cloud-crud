package com.example.microserviceuser.service

import com.example.microserviceuser.FeignClientInterface
import com.example.microserviceuser.domain.User
import com.example.microserviceuser.dto.UserDto
import com.example.microserviceuser.exception.CannotFindUserException
import com.example.microserviceuser.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.sql.SQLIntegrityConstraintViolationException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val feignClientInterface: FeignClientInterface
) {
    fun registerUser(username: String, password: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        var user = User(username = username, password = encodedPassword)
        try {
            user = userRepository.save(user)
        } catch (e: Exception){
            println("zzzz")
            throw IllegalArgumentException("데이터 중복")
        }


        return user
    }

    fun authenticate(username: String, password: String): Boolean {
        val user = userRepository.findByUsername(username)
        return user != null && passwordEncoder.matches(password, user.password)
    }

    fun getMyInfo(authorization: String, username: String): UserDto.UserResponse {
        val myPostCount: Long = feignClientInterface.getMyPostCount(authorization)
        val user = userRepository.findByUsername(username)
            ?: throw CannotFindUserException(username)
        return UserDto.UserResponse(user.id, user.username, myPostCount)
    }
}