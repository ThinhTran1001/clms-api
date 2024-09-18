package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;
import java.util.List;
import java.io.IOException;
import java.util.UUID;
import java.io.File;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.threeluaclmsapi.dto.request.CreateLessonRequest;
import vn.threeluaclmsapi.model.Attachment;
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
    public void createLesson(CreateLessonRequest request) throws IOException {
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
                        .lesson(lesson)
                        .build();

                attachments.add(attachment);
            }

            lesson.setAttachments(attachments);
        }

        lessonRepository.save(lesson);
    }
}
