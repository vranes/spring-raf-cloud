package rs.raf.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.demo.model.ErrorMessage;
import rs.raf.demo.model.Node;
import rs.raf.demo.model.Permissions;
import rs.raf.demo.model.User;
import rs.raf.demo.services.ErrorMessageService;
import rs.raf.demo.services.NodeService;
import rs.raf.demo.services.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/errors")
public class ErrorMessageController {

    private final ErrorMessageService errorMessageService;
    private final UserService userService;

    public ErrorMessageController(ErrorMessageService errorMessageService, UserService userService) {
        this.errorMessageService = errorMessageService;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> all(){
        if (!authCheck()) {
            return ResponseEntity.status(403).build();
        }

        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_search_nodes)) {
            return ResponseEntity.status(403).build();
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(username);
        if(user == null) {
            return ResponseEntity.badRequest().build();
        }

        List<ErrorMessage> errors = errorMessageService.findAllByUser(user);

        return new ResponseEntity<>(errors, HttpStatus.OK);
    };

    private boolean authCheck(){
        if (SecurityContextHolder.getContext() == null ||
                SecurityContextHolder.getContext().getAuthentication() == null ||
                SecurityContextHolder.getContext().getAuthentication().getAuthorities() == null)
            return false;
        return true;
    }
}
