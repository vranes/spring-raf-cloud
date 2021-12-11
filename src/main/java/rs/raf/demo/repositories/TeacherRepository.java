package rs.raf.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import rs.raf.demo.model.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
}
