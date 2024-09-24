package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import vn.threeluaclmsapi.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    boolean existsBySubjectCode(String subjectCode);

    List<Subject> findByCategoryId(String categoryId);

    boolean existsBySubjectCodeAndIdNot(String subjectCode, String id);
}
