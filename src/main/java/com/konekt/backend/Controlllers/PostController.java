package com.konekt.backend.Controlllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konekt.backend.Entities.Post;
import com.konekt.backend.Services.IPostService;

import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private IPostService iPostService;

    @GetMapping("/post")
    public ResponseEntity<?> getAllPostUser() {
        return ResponseEntity.status(HttpStatus.OK).body(iPostService.showPostUser());
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iPostService.addPost(post));
    }
    
    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody Post post, @PathVariable Long id ) {
        Optional<Post> optionalPost = iPostService.updatePost(id, post);
        if (!optionalPost.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalPost);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @RequestBody Post post){
        Optional<Post> optionalPost = iPostService.deletePost(id, post);
        if (optionalPost.isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
