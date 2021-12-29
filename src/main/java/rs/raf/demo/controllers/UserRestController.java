package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Permissions;
import rs.raf.demo.model.User;
import rs.raf.demo.services.UserService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <?> getAllUsers(){
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }

        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_read_users)) {
            return ResponseEntity.badRequest().build();
            //return ResponseEntity.status(403).build();
        }

        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    };

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@RequestParam("id") Long id){
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }

        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_read_users)) {
            return ResponseEntity.badRequest().build();
            //return ResponseEntity.status(403).build();
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
    public ResponseEntity<?> createUser(@RequestBody User user){
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_create_users)) {
            return ResponseEntity.badRequest().build();
            //return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody User user){
        System.out.println("updating");
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_update_users)) {
            return ResponseEntity.badRequest().build();
            //return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_delete_users)) {
            return ResponseEntity.badRequest().build();
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
