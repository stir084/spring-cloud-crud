package com.example.microservicepost.controller

import com.example.microservicepost.JwtTokenProvider
import com.example.microservicepost.dto.PostDto
import com.example.microservicepost.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PostController(private val postService: PostService, private val jwtTokenProvider: JwtTokenProvider) {


    @GetMapping("/posts")
    fun getAllPosts(): ResponseEntity<List<PostDto.PostDtoRes>> {
        val posts = postService.getAllPosts()
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
    fun createPost(@RequestHeader("Authorization") authrization: String,
                   @RequestBody postDTO: PostDto.PostDtoReq): ResponseEntity<PostDto.PostDtoRes> {
        val username = jwtTokenProvider.extractUsernameFromToken(authrization)
        val postDTOWithUsername = postDTO.copy(username = username)
        val createdPost = postService.createPost(postDTOWithUsername)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost)
    }

    @PutMapping("/posts/{id}")
    fun updatePost(@RequestHeader("Authorization") authrization: String,
                   @PathVariable id: Long, @RequestBody updatedPostDTO: PostDto.PostDtoReq): ResponseEntity<PostDto.PostDtoRes> {
        val username = jwtTokenProvider.extractUsernameFromToken(authrization)
        val postDTOWithUsername = updatedPostDTO.copy(username = username)

        val updatedPost = postService.updatePost(postDTOWithUsername)
        return if (updatedPost != null) {
            ResponseEntity.ok(updatedPost)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/posts/{id}")
    fun deletePost(@RequestHeader("Authorization") authrization: String,
                   @PathVariable id: Long): ResponseEntity<Unit> {

        val username = jwtTokenProvider.extractUsernameFromToken(authrization)

        postService.deletePost(id, username)
        return ResponseEntity.noContent().build()
    }
}