package rs.raf.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String password;
    private List<String> permissions = new ArrayList<>();

    public UserResponse(User u){
        this.id = u.getId();
        this.email = u.getEmail();
        this.name = u.getName();
        this.surname = u.getSurname();
        this.password = u.getPassword();
        this.permissions = new ArrayList<>();
        for(Permission p: u.getPermissions()){
            this.permissions.add(p.getID().toString());
        }
    }

    public UserResponse(){}
}
