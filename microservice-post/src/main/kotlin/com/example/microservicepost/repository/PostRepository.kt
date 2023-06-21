package com.example.microservicepost.repository

import com.example.microservicepost.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostRepository : JpaRepository<Post, Long>{
    fun findByUsername(username: String): List<Post>
}