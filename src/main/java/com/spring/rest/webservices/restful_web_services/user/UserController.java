package com.spring.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.spring.rest.webservices.restful_web_services.jpa.UserRepository;
import com.spring.rest.webservices.restful_web_services.post.Post;
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

    private UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());

        // Link pointing to the retrieveUsers method
        WebMvcLinkBuilder link = linkTo((methodOn(this.getClass())).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = repository.save(user);
        
        // Indicates the location of the created user to the header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsByUserId(@PathVariable int id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }
        return user.get().getPosts();
    }

}