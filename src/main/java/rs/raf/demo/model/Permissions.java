package rs.raf.demo.model;

import org.springframework.security.core.GrantedAuthority;

public enum Permissions implements GrantedAuthority {

    can_create_users, can_read_users, can_update_users, can_delete_users,
    can_search_machines, can_start_machines, can_stop_machines, can_restart_machines, can_create_machines, can_destroy_machines;

    @Override
    public String getAuthority() {
        return name();
    }
}
