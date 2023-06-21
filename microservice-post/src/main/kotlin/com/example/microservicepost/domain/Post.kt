package com.example.microservicepost.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Post protected constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var username: String,
    var title: String,
    var content: String,
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    // 정적 팩토리 메서드
    companion object {
        fun createPost(username: String?, title: String, content: String): Post {
            val validUsername = username ?: throw IllegalArgumentException("Username must not be null")
            return Post(username = validUsername, title = title, content = content)
        }
    }
}