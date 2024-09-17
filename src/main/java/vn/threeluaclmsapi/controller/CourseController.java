package vn.threeluaclmsapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.threeluaclmsapi.dto.request.CreateCourseRequest;
import vn.threeluaclmsapi.service.CourseService;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Void> createCourse(@RequestBody CreateCourseRequest request) {
        courseService.createCourse(request);
        return ResponseEntity.ok().build();
    }
}
