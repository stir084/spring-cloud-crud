package com.example.microservicepost.controller

import com.example.microservicepost.dto.PostDto
import com.example.microservicepost.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PostController(private val postService: PostService) {

    @GetMapping("/posts")
    fun getAllPosts(): ResponseEntity<List<PostDto.PostDtoRes>> {
        val posts = postService.getAllPosts();
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/posts/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<PostDto.PostDtoRes> {
        val post = postService.getPostById(id)
        return if (post != null) {
            ResponseEntity.ok(post)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    @PostMapping("/posts")
    fun createPost(@RequestBody postDTO: PostDto.PostDtoReq): ResponseEntity<PostDto.PostDtoRes> {
        val createdPost = postService.createPost(postDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost)
    }

    @PutMapping("/posts/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody updatedPostDTO: PostDto.PostDtoReq): ResponseEntity<PostDto.PostDtoRes> {
        val updatedPost = postService.updatePost(id, updatedPostDTO)
        return if (updatedPost != null) {
            ResponseEntity.ok(updatedPost)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/posts/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Unit> {
        postService.deletePost(id)
        return ResponseEntity.noContent().build()
    }
}