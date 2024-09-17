package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.dto.request.CreateLessonRequest;

public interface LessonService {
    void createLesson(CreateLessonRequest request);
}
