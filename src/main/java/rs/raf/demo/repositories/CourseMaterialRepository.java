package rs.raf.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import rs.raf.demo.model.CourseMaterial;

public interface CourseMaterialRepository extends CrudRepository<CourseMaterial, Long> {
}
