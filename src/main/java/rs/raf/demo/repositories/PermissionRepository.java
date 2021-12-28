package rs.raf.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import rs.raf.demo.model.Permission;

public interface PermissionRepository extends CrudRepository<Permission, String> {
}
