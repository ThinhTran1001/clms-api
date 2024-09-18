package vn.threeluaclmsapi.service;

import java.io.IOException;

import vn.threeluaclmsapi.dto.request.CreateLessonRequest;

public interface LessonService {
    void createLesson(CreateLessonRequest request) throws IOException;
}
