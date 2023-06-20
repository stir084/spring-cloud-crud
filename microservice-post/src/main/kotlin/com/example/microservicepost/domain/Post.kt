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
    var title: String,
    var content: String,
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    // 정적 팩토리 메서드
    companion object {
        fun createPost(title: String, content: String): Post {
            return Post(title = title, content = content)
        }
    }
}