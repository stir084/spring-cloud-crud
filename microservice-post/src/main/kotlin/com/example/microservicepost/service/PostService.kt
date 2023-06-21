package com.example.microservicepost.service

import com.example.microservicepost.domain.Post
import com.example.microservicepost.dto.PostDto
import com.example.microservicepost.repository.PostRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

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
        val post = Post.createPost(postDto.username, postDto.title, postDto.content)
        val savedPost = postRepository.save(post)
        return PostDto.PostDtoRes(savedPost.id, savedPost.title, savedPost.content, savedPost.createdAt)
    }

    fun updatePost(postDtoReq: PostDto.PostDtoReq): PostDto.PostDtoRes? {

        val existingPost = postRepository.findById(postDtoReq.id).orElse(null)

        if(!postDtoReq.username.equals(existingPost.username)){
            throw IllegalArgumentException("권한 없음")
        }

        if (existingPost != null) {
            existingPost.title = postDtoReq.title
            existingPost.content = postDtoReq.content
            var updatedPost = postRepository.save(existingPost)

            return PostDto.PostDtoRes(updatedPost.id, updatedPost.title, updatedPost.content, updatedPost.createdAt)
        }
        return null
    }

    fun deletePost(id: Long, username: String) {
        val existingPost = postRepository.findById(id).get()
        if (existingPost != null) {
            if(!username.equals(existingPost.username)){
                throw IllegalArgumentException("삭제 권한 없음")
            }
        } else {
            throw IllegalArgumentException("게시글 없음")
        }
        postRepository.deleteById(id)
    }
}