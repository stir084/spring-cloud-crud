package com.example.microservicepost.controller

import com.example.microservicepost.domain.Post
import com.example.microservicepost.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PostController(private val postService: PostService) {

    @GetMapping("/posts")
    fun getAllPosts(): ResponseEntity<List<Post>> {
        val posts = postService.getAllPosts()
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/posts/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<Post> {
        val post = postService.getPostById(id)
        return if (post != null) {
            ResponseEntity.ok(post)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/posts")
    fun createPost(@RequestBody post: Post): ResponseEntity<Post> {
        val createdPost = postService.createPost(post)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost)
    }

    @PutMapping("/posts/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody updatedPost: Post): ResponseEntity<Post> {
        val post = postService.updatePost(id, updatedPost)
        return if (post != null) {
            ResponseEntity.ok(post)
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