package com.micro.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserSubscribeController {
    private final UserService userService;

    @GetMapping("/users")
    public List<UserDto> allUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> getBook(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser( @RequestBody UserDto user) throws URISyntaxException {
        UserDto result = userService.save(user);
        return ResponseEntity.created(new URI("/api/v1/books/" + result.getId()))
                .body(result);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> updateUser( @PathVariable Long id, @RequestBody UserDto book) {
        return ResponseEntity.ok().body(userService.save(book));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<UserDto> addSubscriptionUser(@PathVariable Long id, @PathVariable Long idSub) throws URISyntaxException {

        UserDto result = userService.addSubscriptionByUserId(id, idSub);;
        return ResponseEntity.created(new URI("/api/v1/users/" + result.getId())).body(result);
    }

    @GetMapping("/users/{id}/subscriptions")
    public List<UserDto> userAllSubscriptions() {
        return userService.findAll();
    }

    @DeleteMapping("/users/{id}/subscriptions/{sup_id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @PathVariable Long supId) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/subscriptions/top")
    public List<Subscription> topUsersSubscriptions() {
        return userService.getTopUsersSubscriptions();
    }
}