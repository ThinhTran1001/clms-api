package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.threeluaclmsapi.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
}
