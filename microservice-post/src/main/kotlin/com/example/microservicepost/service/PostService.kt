package com.example.microservicepost.service

import com.example.microservicepost.domain.Post
import com.example.microservicepost.dto.PostDto
import com.example.microservicepost.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(private val postRepository: PostRepository) {
    fun getAllPosts(): List<PostDto.PostDtoRes> {
        return postRepository.findAll().map { post ->
            PostDto.PostDtoRes(post.id, post.title, post.content, post.createdAt)
        }
    }

    fun getPostById(id: Long): PostDto.PostDtoRes {
        val post = postRepository.findById(id).orElse(null)
        return PostDto.PostDtoRes(post.id, post.title, post.content, post.createdAt)
    }

    fun createPost(postDto: PostDto.PostDtoReq): PostDto.PostDtoRes {
        val post = Post.createPost(postDto.title, postDto.content)
        val savedPost = postRepository.save(post)
        return PostDto.PostDtoRes(savedPost.id, savedPost.title, savedPost.content, savedPost.createdAt)
    }

    fun updatePost(id: Long, postDtoReq: PostDto.PostDtoReq): PostDto.PostDtoRes? {
        val existingPost = postRepository.findById(id).orElse(null)
        if (existingPost != null) {
            existingPost.title = postDtoReq.title
            existingPost.content = postDtoReq.content
            var updatedPost = postRepository.save(existingPost)

            return PostDto.PostDtoRes(updatedPost.id, updatedPost.title, updatedPost.content, updatedPost.createdAt)
        }
        return null
    }

    fun deletePost(id: Long) {
        postRepository.deleteById(id)
    }
}