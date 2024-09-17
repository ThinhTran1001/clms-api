package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.threeluaclmsapi.model.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, String> {
    List<Lesson> findByCourseId(String course);
}