package vn.threeluaclmsapi.service.impl;

import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import vn.threeluaclmsapi.dto.request.CreateCourseRequest;
import vn.threeluaclmsapi.model.Course;
import vn.threeluaclmsapi.model.Subject;
import vn.threeluaclmsapi.model.Semester;
import vn.threeluaclmsapi.model.Classroom;
import vn.threeluaclmsapi.repository.CourseRepository;
import vn.threeluaclmsapi.repository.SubjectRepository;
import vn.threeluaclmsapi.repository.SemesterRepository;
import vn.threeluaclmsapi.repository.ClassroomRepository;
import vn.threeluaclmsapi.service.CourseService;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

        private final CourseRepository courseRepository;
        private final SubjectRepository subjectRepository;
        private final SemesterRepository semesterRepository;
        private final ClassroomRepository classroomRepository;

        @Transactional
        @Override
        public void createCourse(CreateCourseRequest request) {
                if (request.getSubjectId() == null || request.getSemesterId() == null) {
                        throw new IllegalArgumentException("Subject ID and Semester ID cannot be null");
                }

                Subject subject = subjectRepository.findById(UUID.fromString(request.getSubjectId()))
                                .orElseThrow(() -> new RuntimeException("Subject not found"));

                Semester semester = semesterRepository.findById(UUID.fromString(request.getSemesterId()))
                                .orElseThrow(() -> new RuntimeException("Semester not found"));

                Classroom classroom = Optional.ofNullable(request.getClassroomId())
                                .map(classroomId -> classroomRepository.findById(UUID.fromString(classroomId))
                                                .orElseThrow(() -> new RuntimeException("Classroom not found")))
                                .orElse(null);

                Course course = Course.builder()
                                .subject(subject)
                                .semester(semester)
                                .classroom(classroom)
                                .build();

                courseRepository.save(course);
        }
}
