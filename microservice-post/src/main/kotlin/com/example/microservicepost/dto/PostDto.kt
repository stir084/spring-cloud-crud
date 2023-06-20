package com.example.microservicepost.dto

import java.time.LocalDateTime

class PostDto {
    data class PostDtoReq(
        val id: Long,
        val title: String,
        val content: String,
        val createdAt: LocalDateTime
    )

    data class PostDtoRes(
        val id: Long,
        val title: String,
        val content: String,
        val createdAt: LocalDateTime
    )
}