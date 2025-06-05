package com.konekt.backend.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.konekt.backend.Entities.Post;
import com.konekt.backend.Repositories.PostRepository;

@Service
public class PostServiceImpl implements IPostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public Post addPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> showPostUser() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Post> updatePost(Long id, Post post) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post postBD = optionalPost.orElseThrow();
       if (optionalPost.isPresent() && postBD.getAuthor().getId().equals(post.getAuthor().getId())) {
        postBD.setUrlImage(post.getUrlImage());
        postBD.setContent(post.getContent());
        return Optional.of(postRepository.save(postBD));
       }else{
        return Optional.empty();
       }
    }

    @Override
    @Transactional
    public Optional<Post> deletePost(Long id, Post post) {
       Optional<Post> postOptional = postRepository.findById(id);
       Post postBD = postOptional.orElseThrow();
       if (postOptional.isPresent() && postBD.getAuthor().getId().equals(post.getAuthor().getId())) {
        postOptional.ifPresent(item -> postRepository.delete(item));
       }
       return postOptional;
    }

}
