package vn.threeluaclmsapi.service;

import java.io.IOException;
import java.util.List;

import vn.threeluaclmsapi.dto.request.lesson.CreateLessonRequest;
import vn.threeluaclmsapi.dto.request.lesson.UpdateLessonRequest;
import vn.threeluaclmsapi.dto.response.LessonResponse;

public interface LessonService {
    void createLesson(CreateLessonRequest request) throws IOException;

    List<LessonResponse> getAllLessons();

    LessonResponse getLessonById(String lessonId);

    void updateLesson(UpdateLessonRequest request);

    void deleteLesson(String lessonId);

    void updateLessonStatus(String lessonId);
}
