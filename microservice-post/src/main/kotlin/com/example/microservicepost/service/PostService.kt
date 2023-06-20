package com.example.microservicepost.service

import com.example.microservicepost.domain.Post
import com.example.microservicepost.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(private val postRepository: PostRepository) {
    fun getAllPosts(): List<Post> {
        return postRepository.findAll()
    }

    fun getPostById(id: Long): Post? {
        return postRepository.findById(id).orElse(null)
    }

    fun createPost(post: Post): Post {
        return postRepository.save(post)
    }

    fun updatePost(id: Long, updatedPost: Post): Post? {
        val existingPost = postRepository.findById(id).orElse(null)
        if (existingPost != null) {
            existingPost.title = updatedPost.title
            existingPost.content = updatedPost.content
            return postRepository.save(existingPost)
        }
        return null
    }

    fun deletePost(id: Long) {
        postRepository.deleteById(id)
    }
}