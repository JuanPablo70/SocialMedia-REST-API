package com.spring.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.spring.rest.webservices.restful_web_services.jpa.PostRepository;
import com.spring.rest.webservices.restful_web_services.jpa.UserRepository;
import com.spring.rest.webservices.restful_web_services.post.Post;
import com.spring.rest.webservices.restful_web_services.post.PostNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private UserRepository userRepository;

    private PostRepository postRepository;

    public UserController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " was not found");
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());

        // Link pointing to the retrieveUsers method
        WebMvcLinkBuilder link = linkTo((methodOn(this.getClass())).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        
        // Indicates the location of the created user to the header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsByUserId(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " was not found");
        }
        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " was not found");
        }

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        // Indicates the location of the created user to the header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public EntityModel<Post> retrievePostByUserIdAndPostId(@PathVariable int userId, @PathVariable int postId) {
        Post post = postRepository.findByIdAndUserId(postId, userId);
        if (post == null) {
            throw new PostNotFoundException("User " + userId + " with Post " + postId + " was not found");
        }

        EntityModel<Post> entityModel = EntityModel.of(post);

        // Link pointing to the retrievePostsByUserId method
        WebMvcLinkBuilder link = linkTo((methodOn(this.getClass())).retrievePostsByUserId(userId));
        entityModel.add(link.withRel("all-user-posts"));

        return entityModel;
    }

}