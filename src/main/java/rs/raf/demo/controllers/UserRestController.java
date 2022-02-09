package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Permissions;
import rs.raf.demo.model.User;
import rs.raf.demo.model.UserResponse;
import rs.raf.demo.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserRestController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <?> getAllUsers(){
        if (!authCheck()) {
            //return ResponseEntity.internalServerError().body(null);
            return ResponseEntity.status(403).build();
        }

        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_read_users)) {
            //return ResponseEntity.badRequest().build();
            return ResponseEntity.status(403).build();
        }

        List<User> users = userService.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User u: users){
            userResponses.add(new UserResponse(u));
        }
        return ResponseEntity.ok(userResponses);
    };

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@RequestParam("id") Long id){
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }

        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_read_users)) {
            //return ResponseEntity.badRequest().build();
            return ResponseEntity.status(403).build();
        }

        Optional<User> optionalUser = userService.findById(id);
        if(optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserResponse userResponse){
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_create_users)) {
            return ResponseEntity.status(403).build();
        }
        User optionalUser = userService.findByEmail(userResponse.getEmail());
        if(optionalUser != null) {
            return ResponseEntity.badRequest().build();
        }
        userResponse.setPassword(this.passwordEncoder.encode(userResponse.getPassword()));
        User user = new User(userResponse);
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UserResponse userResponse){
        System.out.println("updating");
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_update_users)) {
            return ResponseEntity.status(403).build();
        }
        User user = new User(userResponse);
        return ResponseEntity.ok(new UserResponse(userService.save(user)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_delete_users)) {
            return ResponseEntity.status(403).build();
        }
        Optional<User> optionalUser = userService.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            for (int i = 0; i < user.getPermissions().size(); i++) {
                user.getPermissions().get(i).removeUser(user);
            }
            userService.deleteById(id);

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean authCheck(){
        if (SecurityContextHolder.getContext() == null ||
                SecurityContextHolder.getContext().getAuthentication() == null ||
                SecurityContextHolder.getContext().getAuthentication().getAuthorities() == null)
            return false;
        return true;
    }

}
