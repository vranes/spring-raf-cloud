package rs.raf.demo.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.demo.model.*;
import rs.raf.demo.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class BootstrapData implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(PermissionRepository permissionRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Data...");

        Permissions[] PERMISSION_LIST = {Permissions.can_create_users, Permissions.can_read_users, Permissions.can_update_users, Permissions.can_delete_users,
                Permissions.can_search_nodes, Permissions.can_create_nodes, Permissions.can_destroy_nodes, Permissions.can_restart_nodes, Permissions.can_start_nodes, Permissions.can_stop_nodes};

        List<Permission> permissions = new ArrayList<>();
        for (int i = 0; i < PERMISSION_LIST.length; i++) {

            Permission permission = new Permission(PERMISSION_LIST[i]);
            permissions.add(permission);

            System.out.println(permissionRepository.save(permission));
        }

        User user1 = new User();
        user1.setEmail("user1@gmail.com");
        user1.setName("name1");
        user1.setSurname("surname1");
        user1.setPassword(this.passwordEncoder.encode("user1"));
        for(Permission p: permissions){
            user1.addPermission(p);
        }
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("user2@gmail.com");
        user2.setName("name2");
        user2.setSurname("surname2");
        user2.setPassword(this.passwordEncoder.encode("user2"));
        userRepository.save(user2);


        System.out.println("Data loaded!");
        System.out.println("2000-10-31T01:30:00.000-05:00");
        System.out.println("2000-2-14T01:30:00.000-05:00");
    }
}
