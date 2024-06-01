package com.spring.rest.webservices.restful_web_services.jpa;

import com.spring.rest.webservices.restful_web_services.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findByIdAndUserId(int postId, int userId);

}
