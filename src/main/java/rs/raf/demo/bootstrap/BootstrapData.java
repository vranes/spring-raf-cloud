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

        String[] FIRST_NAME_LIST = {"John-James", "Justine", "Ahsan", "Leja", "Jad", "Vernon", "Cara", "Eddison", "Eira", "Emily"};
        String[] LAST_NAME_LIST = {"Booker", "Summers", "Reyes", "Rahman", "Crane", "Cairns", "Hebert", "Bradshaw", "Shannon", "Phillips"};
        String[] PERMISSION_LIST = {"can_create_users", "can_read_users", "can_update_users", "can_delete_users"};

        Random random = new Random();

        List<Permission> permissions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            Permission permission = new Permission(PERMISSION_LIST[i]);
            permissions.add(permission);

            System.out.println(permissionRepository.save(permission));
        }

        User user1 = new User();
        user1.setEmail("user1@gmail.com");
        user1.setName("name1");
        user1.setSurname("surname1");
        user1.setPassword(this.passwordEncoder.encode("user1"));
        user1.getPermissions().addAll(permissions);
        System.out.println(userRepository.save(user1));


        System.out.println("Data loaded!");
    }
}
