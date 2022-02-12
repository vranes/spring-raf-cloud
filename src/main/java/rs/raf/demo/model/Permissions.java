package rs.raf.demo.model;

import org.springframework.security.core.GrantedAuthority;

public enum Permissions implements GrantedAuthority {

    can_create_users, can_read_users, can_update_users, can_delete_users,
    can_search_nodes, can_start_nodes, can_stop_nodes, can_restart_nodes, can_create_nodes, can_destroy_nodes;

    @Override
    public String getAuthority() {
        return name();
    }
}
