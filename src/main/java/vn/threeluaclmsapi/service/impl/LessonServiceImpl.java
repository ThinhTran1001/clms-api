package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.dto.request.CreateLessonRequest;
import vn.threeluaclmsapi.model.Course;
import vn.threeluaclmsapi.model.Lesson;
import vn.threeluaclmsapi.repository.CourseRepository;
import vn.threeluaclmsapi.repository.LessonRepository;
import vn.threeluaclmsapi.service.LessonService;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public void createLesson(CreateLessonRequest request) {
        if (request.getLessonTitle() == null || request.getLessonTitle().isEmpty()) {
            throw new IllegalArgumentException("Lesson title cannot be null or empty");
        }
        if (request.getCourseId() == null || request.getCourseId().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be null or empty");
        }

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Lesson lesson = Lesson.builder()
                .lessonTitle(request.getLessonTitle())
                .lessonDescription(request.getLessonDescription())
                .course(course)
                .build();
        lessonRepository.save(lesson);
    }
}
