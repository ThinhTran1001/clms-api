package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.threeluaclmsapi.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
}
