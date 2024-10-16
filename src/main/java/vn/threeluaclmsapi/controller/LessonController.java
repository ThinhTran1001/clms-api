package vn.threeluaclmsapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.threeluaclmsapi.dto.request.course.CreateCourseRequest;
import vn.threeluaclmsapi.dto.request.lesson.CreateLessonRequest;
import vn.threeluaclmsapi.dto.request.lesson.UpdateLessonRequest;
import vn.threeluaclmsapi.dto.response.LessonResponse;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.service.LessonService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
@Slf4j
public class LessonController {

    private final LessonService lessonService;

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping
    public ResponseData<String> createLesson(@ModelAttribute @Valid CreateLessonRequest request) throws IOException {
        lessonService.createLesson(request);
        return new ResponseData<>("201", "Lesson created successfully");
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ADMIN')")
    @GetMapping
    public ResponseData<List<LessonResponse>> getAllLessons() {
        List<LessonResponse> lessons = lessonService.getAllLessons();
        return new ResponseData<>(HttpStatus.OK.toString(), "Fetched all lessons successfully", lessons);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @GetMapping("/{lessonId}")
    public ResponseData<LessonResponse> getLessonById(@PathVariable String lessonId) {
        LessonResponse lesson = lessonService.getLessonById(lessonId);
        return new ResponseData<>(HttpStatus.OK.toString(), "Fetched lesson successfully", lesson);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PutMapping("/{lessonId}")
    public ResponseData<?> updateLesson(@RequestBody @Valid UpdateLessonRequest request) {
        lessonService.updateLesson(request);
        return new ResponseData<>(HttpStatus.OK.toString(), "Updated lesson successfully");
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @DeleteMapping("/{lessonId}")
    public ResponseData<?> deleteLesson(@PathVariable String lessonId) {
        lessonService.deleteLesson(lessonId);
        return new ResponseData<>(HttpStatus.OK.toString(), "Deleted lesson successfully");
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PutMapping("/{lessonId}/status")
    public ResponseData<?> updateLessonStatus(@PathVariable String lessonId) {
        lessonService.updateLessonStatus(lessonId);
        return new ResponseData<>(HttpStatus.OK.toString(), "Updated lesson status successfully");
    }
}
