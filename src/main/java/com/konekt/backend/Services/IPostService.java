package com.konekt.backend.Services;

import java.util.List;
import java.util.Optional;

import com.konekt.backend.Entities.Post;

public interface IPostService {
    Post addPost(Post post);
    List<Post> showPostUser();
    Optional<Post> updatePost(Long id, Post post);
    Optional<Post> deletePost(Long id, Post post);
}
