package com.example.microserviceuser.domain

import jakarta.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true)
    val username: String,
    val password: String,
    // 추가 필드 및 관계 설정
)