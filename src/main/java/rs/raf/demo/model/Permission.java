package rs.raf.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Permission {

    public Permission (Permissions id){
        this.ID = id;
    }

    public Permission (){}

    @Id
    private Permissions ID;

    @ManyToMany
    @JoinTable(
            name = "USERS_PERMISSIONS",
            joinColumns = @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    )
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    public void removeUser(User user) {
        users.remove(user);
        user.getPermissions().remove(this);
    }
}
