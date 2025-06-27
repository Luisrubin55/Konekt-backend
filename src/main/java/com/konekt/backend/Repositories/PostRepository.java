package com.konekt.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konekt.backend.Entities.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
