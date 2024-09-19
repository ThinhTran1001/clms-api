package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.threeluaclmsapi.dto.response.StudentResponse;
import vn.threeluaclmsapi.model.Classroom;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String> {
    Optional<Classroom> findByClassroomName(String classroomName);

    @Query(value = "SELECT u.full_name, s.student_code, s.major " +
            "FROM tbl_user u " +
            "JOIN tbl_student s ON u.id = s.user_id " +
            "JOIN tbl_student_classroom sc ON sc.user_id = u.id " +
            "JOIN tbl_classroom c ON c.id = sc.classroom_id " +
            "WHERE c.id LIKE %:classroomId%", nativeQuery = true)
    List<Object[]> findStudentsByClassroomId(String classroomId);


}
