package vn.threeluaclmsapi.controller;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.threeluaclmsapi.dto.request.course.CreateCourseRequest;
import vn.threeluaclmsapi.dto.request.course.UpdateCourseRequest;
import vn.threeluaclmsapi.dto.response.CourseDetailsResponse;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.service.CourseService;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER')")
    @GetMapping
    public ResponseData<List<CourseDetailsResponse>> getAllCourses() {
        List<CourseDetailsResponse> courses = courseService.listAllCourses();
        if (courses.isEmpty()) {
            return new ResponseData<>("404", "No courses found");
        }
        return new ResponseData<>("200", "Success", courses);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping
    public ResponseData<String> createCourse(@ModelAttribute @Valid CreateCourseRequest request) throws IOException {
        courseService.createCourse(request);
        return new ResponseData<>("201", "Course created successfully");
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @GetMapping("/{courseId}")
    public ResponseData<CourseDetailsResponse> viewCourse(@PathVariable UUID courseId) {
        CourseDetailsResponse courseDetails = courseService.viewCourse(courseId);
        if (courseDetails != null) {
            return new ResponseData<>("200", "Course found", courseDetails);
        } else {
            return new ResponseData<>("404", "Course not found");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{courseId}")
    public ResponseData<String> updateCourse(@PathVariable UUID courseId,
            @ModelAttribute @Valid UpdateCourseRequest request) {
        try {
            courseService.updateCourse(courseId, request);
            return new ResponseData<>("200", "Course updated successfully");
        } catch (Exception e) {
            return new ResponseData<>("400", "Course update failed: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{courseId}/status")
    public ResponseData<String> updateCourseStatus(@PathVariable String courseId) {
        courseService.updateCourseStatus(courseId);
        return new ResponseData<>("200", "Course status updated successfully");
    }
}
