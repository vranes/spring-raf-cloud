package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.*;
import rs.raf.demo.services.NodeService;
import rs.raf.demo.services.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/nodes")
public class NodeRestController {

    private final NodeService nodeService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public NodeRestController(NodeService nodeService, PasswordEncoder passwordEncoder, UserService userService) {
        this.nodeService = nodeService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping(value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchNodes(@RequestParam("name") String name, @RequestParam("status") List<String> status, @RequestParam("dateFrom") Date dateFrom, @RequestParam("dateTo") Date dateTo){
        if (!authCheck()) {
            return ResponseEntity.status(403).build();
        }

        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_search_machines)) {
            return ResponseEntity.status(403).build();
        }

        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
        User user = userService.findByEmail(userDetails.getUsername());
        if(user == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Node> nodes = nodeService.search(user, name, status, dateFrom, dateTo);
        return ResponseEntity.ok(nodes);
    };

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNode(@RequestParam("name") String name){
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_create_machines)) {
            return ResponseEntity.status(403).build();
        }

        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
        User user = userService.findByEmail(userDetails.getUsername());

        if(user == null) {
            return ResponseEntity.badRequest().build();
        }

        Node node = new Node();
        node.setName(name);
        node.setActive(true);
        node.setCreatedBy(user);
        node.setStatus(Status.STOPPED);

//        user.getNodes().add(node);
//        userService.save(user); TODO

        return ResponseEntity.ok(nodeService.save(node));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> destroyNode(@PathVariable Long id) {
        if (!authCheck()) {
            return ResponseEntity.internalServerError().body(null);
        }
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Permissions.can_delete_users)) {
            return ResponseEntity.status(403).build();
        }

        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
        User user = userService.findByEmail(userDetails.getUsername());

        Optional<Node> optionalNode = nodeService.findByIdAndUser(id, user);

        if(!optionalNode.isPresent())
            return ResponseEntity.notFound().build();

        Node node = optionalNode.get();

        if (node.getStatus() == Status.RUNNING)
            return ResponseEntity.status(403).build();

        nodeService.deleteNode(node);
        return ResponseEntity.ok().build();
    }

    private boolean authCheck(){
        if (SecurityContextHolder.getContext() == null ||
                SecurityContextHolder.getContext().getAuthentication() == null ||
                SecurityContextHolder.getContext().getAuthentication().getAuthorities() == null)
            return false;
        return true;
    }
}
