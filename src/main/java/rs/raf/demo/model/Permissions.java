package rs.raf.demo.model;

import org.springframework.security.core.GrantedAuthority;

public enum Permissions implements GrantedAuthority {

    can_create_users, can_read_users, can_update_users, can_delete_users;

    @Override
    public String getAuthority() {
        return name();
    }
}
