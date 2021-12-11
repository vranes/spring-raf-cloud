package rs.raf.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import rs.raf.demo.model.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
