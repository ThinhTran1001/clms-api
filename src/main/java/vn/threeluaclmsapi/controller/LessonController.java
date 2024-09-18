package vn.threeluaclmsapi.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.threeluaclmsapi.dto.request.CreateLessonRequest;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.service.LessonService;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
@Slf4j
public class LessonController {
    private final LessonService lessonService;

    @PostMapping()
    public ResponseData<?> createLesson(@RequestBody CreateLessonRequest request) throws IOException {
        lessonService.createLesson(request);
        return new ResponseData<>(HttpStatus.CREATED.toString(), "Created lesson successfully!");
    }
}
