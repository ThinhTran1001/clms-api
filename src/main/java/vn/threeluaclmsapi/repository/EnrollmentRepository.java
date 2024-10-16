package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.threeluaclmsapi.model.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
}
