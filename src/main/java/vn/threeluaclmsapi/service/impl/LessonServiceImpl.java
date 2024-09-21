package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.stream.Collectors;
import java.util.List;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;
import java.io.File;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.threeluaclmsapi.dto.request.lesson.CreateLessonRequest;
import vn.threeluaclmsapi.dto.request.lesson.UpdateLessonRequest;
import vn.threeluaclmsapi.dto.response.LessonResponse;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.model.Attachment;
import vn.threeluaclmsapi.model.Course;
import vn.threeluaclmsapi.model.Lesson;
import vn.threeluaclmsapi.model.Subject;
import vn.threeluaclmsapi.repository.CourseRepository;
import vn.threeluaclmsapi.repository.LessonRepository;
import vn.threeluaclmsapi.service.LessonService;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public void createLesson(CreateLessonRequest request) throws IOException {
        try {
            if (request.getLessonTitle() == null || request.getLessonTitle().isEmpty()) {
                throw new IllegalArgumentException("Lesson title cannot be null or empty");
            }
            if (request.getCourseId() == null || request.getCourseId().isEmpty()) {
                throw new IllegalArgumentException("Course ID cannot be null or empty");
            }

            Logger logger = Logger.getLogger(LessonServiceImpl.class.getName());
            logger.info("course with title: " + request.getCourseId());

            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

            Lesson lesson = Lesson.builder()
                    .lessonTitle(request.getLessonTitle())
                    .lessonDescription(request.getLessonDescription())
                    .course(course)
                    .status(true)
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
                    String filePath = uploadDir + File.separator + uniqueFileName;
                    file.transferTo(new File(filePath));

                    Attachment attachment = Attachment.builder()
                            .fileName(uniqueFileName)
                            .filePath(filePath)
                            .lesson(lesson)
                            .build();

                    attachments.add(attachment);
                }
                lesson.setAttachments(attachments);
            }

            lessonRepository.save(lesson);
        } catch (Exception e) {
            log.error("Error while creating lesson: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create lesson", e);
        }
    }

    @Override
    public void updateLesson(UpdateLessonRequest request) {
        try {
            Lesson lesson = lessonRepository.findById(request.getLessonId())
                    .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

            if (request.getLessonTitle() != null) {
                lesson.setLessonTitle(request.getLessonTitle());
            }
            if (request.getLessonDescription() != null) {
                lesson.setLessonDescription(request.getLessonDescription());
            }
            if (request.getStatus() != null) {
                lesson.setStatus(request.getStatus());
            }
            if (request.getCourseId() != null) {
                Course course = courseRepository.findById(request.getCourseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
                lesson.setCourse(course);
            }

            String baseDir = new File("").getAbsolutePath();
            String uploadDir = baseDir + File.separator + "uploads" + File.separator;
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
                List<Attachment> attachments = new ArrayList<>();

                for (MultipartFile file : request.getAttachments()) {
                    String uniqueFileName = UUID.randomUUID().toString() + "_"
                            + file.getOriginalFilename();
                    String filePath = uploadDir + uniqueFileName;

                    file.transferTo(new File(filePath));

                    Attachment attachment = Attachment.builder()
                            .fileName(uniqueFileName)
                            .filePath(filePath)
                            .lesson(lesson)
                            .build();

                    attachments.add(attachment);
                }

                lesson.setAttachments(attachments);
            }

            lessonRepository.save(lesson);
        } catch (Exception e) {
            log.error("Error while updating lesson: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update lesson", e);
        }
    }

    @Override
    public void deleteLesson(String lessonId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
            lessonRepository.delete(lesson);
        } catch (Exception e) {
            log.error("Error while deleting lesson: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete lesson", e);
        }
    }

    @Override
    public List<LessonResponse> getAllLessons() {
        try {
            List<Lesson> lessons = lessonRepository.findAll();
            List<LessonResponse> lessonResponses = new ArrayList<>();
            for (Lesson lesson : lessons) {
                LessonResponse lessonResponse = new LessonResponse();
                lessonResponse.setLessonTitle(lesson.getLessonTitle());
                lessonResponse.setLessonDescription(lesson.getLessonDescription());
                lessonResponse.setStatus(lesson.getStatus());
                lessonResponse.setCourseId(lesson.getCourse().getId());
                lessonResponses.add(lessonResponse);
            }
            return lessonResponses;
        } catch (Exception e) {
            log.error("Error while fetching all lessons: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch lessons", e);
        }
    }

    @Override
    public LessonResponse getLessonById(String lessonId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
            LessonResponse response = new LessonResponse();
            response.setLessonTitle(lesson.getLessonTitle());
            response.setLessonDescription(lesson.getLessonDescription());
            response.setStatus(lesson.getStatus());
            response.setCourseId(lesson.getCourse().getId());
            return response;
        } catch (Exception e) {
            log.error("Error while fetching lesson by ID: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch lesson", e);
        }
    }

    @Override
    public void updateLessonStatus(String lessonId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
            lesson.setStatus(!lesson.getStatus());
            lessonRepository.save(lesson);
        } catch (Exception e) {
            log.error("Error while updating lesson status: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update lesson status", e);
        }
    }
}
