package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.threeluaclmsapi.model.Classroom;

import java.util.UUID;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String> {
}
