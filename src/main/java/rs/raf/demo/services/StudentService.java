package rs.raf.demo.services;

import org.springframework.stereotype.Service;
import rs.raf.demo.model.Student;
import rs.raf.demo.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IService<Student, Long> {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public <S extends Student> S save(S student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Long studentID) {
        return studentRepository.findById(studentID);
    }

    @Override
    public List<Student> findAll() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public void deleteById(Long studentID) {
        studentRepository.deleteById(studentID);
    }
}
