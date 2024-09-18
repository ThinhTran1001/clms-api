package vn.threeluaclmsapi.controller;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.threeluaclmsapi.dto.request.CreateCourseRequest;
import vn.threeluaclmsapi.dto.request.UpdateCourseRequest;
import vn.threeluaclmsapi.dto.response.CourseDetailsResponse;
import vn.threeluaclmsapi.service.CourseService;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseDetailsResponse> getAllCourses() {
        return courseService.listAllCourses();
    }

    @PostMapping
    public ResponseEntity<Void> createCourse(@ModelAttribute @Valid CreateCourseRequest request) throws IOException {
        courseService.createCourse(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailsResponse> viewCourse(@PathVariable UUID courseId) {
        CourseDetailsResponse courseDetails = courseService.viewCourse(courseId);
        return ResponseEntity.ok(courseDetails);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Void> updateCourse(@PathVariable UUID courseId, @RequestBody UpdateCourseRequest request) {
        courseService.updateCourse(courseId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok().build();
    }

}
