package vn.threeluaclmsapi.service;

import java.util.List;
import java.util.UUID;
import java.io.IOException;

import vn.threeluaclmsapi.dto.request.course.CreateCourseRequest;
import vn.threeluaclmsapi.dto.request.course.UpdateCourseRequest;
import vn.threeluaclmsapi.dto.response.CourseDetailsResponse;

public interface CourseService {
    void createCourse(CreateCourseRequest request) throws IOException;

    CourseDetailsResponse viewCourse(UUID courseId);

    List<CourseDetailsResponse> listAllCourses();

    void updateCourse(UUID courseId, UpdateCourseRequest request);

    void updateCourseStatus(String courseId);
}
