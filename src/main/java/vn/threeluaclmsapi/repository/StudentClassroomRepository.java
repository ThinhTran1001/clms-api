package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.threeluaclmsapi.model.StudentClassroom;

@Repository
public interface StudentClassroomRepository extends JpaRepository<StudentClassroom, Long> {
}
