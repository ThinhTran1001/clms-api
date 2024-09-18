package vn.threeluaclmsapi.service.impl;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import vn.threeluaclmsapi.dto.request.CreateCourseRequest;
import vn.threeluaclmsapi.dto.request.UpdateCourseRequest;
import vn.threeluaclmsapi.dto.response.CourseDetailsResponse;
import vn.threeluaclmsapi.dto.response.LessonDetailsResponse;
import vn.threeluaclmsapi.model.Course;
import vn.threeluaclmsapi.model.Lesson;
import vn.threeluaclmsapi.model.Subject;
import vn.threeluaclmsapi.model.Semester;
import vn.threeluaclmsapi.model.Attachment;
import vn.threeluaclmsapi.model.Classroom;
import vn.threeluaclmsapi.repository.CourseRepository;
import vn.threeluaclmsapi.repository.LessonRepository;
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
        private final LessonRepository lessonRepository;

        @Override
        public List<CourseDetailsResponse> listAllCourses() {
                List<Course> courses = courseRepository.findAll();

                return courses.stream().map(course -> {
                        CourseDetailsResponse courseResponse = new CourseDetailsResponse();
                        courseResponse.setCourseId(course.getId());
                        courseResponse.setCourseTitle(course.getSubject().getSubjectName());
                        courseResponse.setSubject(course.getSubject().getSubjectName());
                        courseResponse.setSemester(course.getSemester().getSemesterName());
                        return courseResponse;
                }).collect(Collectors.toList());
        }

        @Transactional
        @Override
        public void createCourse(CreateCourseRequest request) throws IOException {
                Subject subject = subjectRepository.findById(request.getSubjectId())
                                .orElseThrow(() -> new RuntimeException("Subject not found"));

                Semester semester = semesterRepository.findById(UUID.fromString(request.getSemesterId()))
                                .orElseThrow(() -> new RuntimeException("Semester not found"));

                Classroom classroom = classroomRepository.findById(request.getClassroomId())
                                .orElseThrow(() -> new RuntimeException("Classroom not found"));

                Course course = Course.builder()
                                .subject(subject)
                                .semester(semester)
                                .classroom(classroom)
                                .build();

                String uploadDir = new File("uploads").getAbsolutePath();

                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdirs();
                }

                if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
                        List<Attachment> attachments = new ArrayList<>();

                        for (MultipartFile file : request.getAttachments()) {
                                String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                                String filePath = uploadDir + uniqueFileName;

                                file.transferTo(new File(filePath));

                                Attachment attachment = Attachment.builder()
                                                .fileName(uniqueFileName)
                                                .filePath(filePath)
                                                .course(course)
                                                .build();

                                attachments.add(attachment);
                        }

                        course.setAttachments(attachments);
                }

                courseRepository.save(course);
        }

        @Override
        public CourseDetailsResponse viewCourse(UUID courseId) {
                Course course = courseRepository.findById(courseId.toString())
                                .orElseThrow(() -> new RuntimeException("Course not found"));

                List<Lesson> lessons = lessonRepository.findByCourseId(courseId.toString());

                CourseDetailsResponse response = new CourseDetailsResponse();
                response.setCourseId(course.getId());
                response.setCourseTitle(course.getSubject().getSubjectName());
                response.setSubject(course.getSubject().getSubjectName());
                response.setSemester(course.getSemester().getSemesterName());

                List<LessonDetailsResponse> lessonDetails = lessons.stream()
                                .map(lesson -> new LessonDetailsResponse(lesson.getLessonTitle(),
                                                lesson.getLessonDescription()))
                                .collect(Collectors.toList());

                response.setLessons(lessonDetails);

                return response;
        }

        @Override
        public void updateCourse(UUID courseId, UpdateCourseRequest request) {
                Course course = courseRepository.findById(courseId.toString())
                                .orElseThrow(() -> new RuntimeException("Course not found"));

                Subject subject = subjectRepository.findById(request.getSubject())
                                .orElseThrow(() -> new RuntimeException("Subject not found"));
                UUID semesterId = UUID.fromString(request.getSemester());
                Semester semester = semesterRepository.findById(semesterId)
                                .orElseThrow(() -> new RuntimeException("Semester not found"));

                course.setSubject(subject);
                course.setSemester(semester);

                courseRepository.save(course);
        }

        @Override
        public void deleteCourse(UUID courseId) {
                courseRepository.deleteById(courseId.toString());
        }

}
