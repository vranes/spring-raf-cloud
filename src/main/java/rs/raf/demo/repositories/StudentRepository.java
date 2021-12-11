package rs.raf.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import rs.raf.demo.model.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
