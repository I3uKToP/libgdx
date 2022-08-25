package v.kiselev.testmanytomany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import v.kiselev.testmanytomany.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
