package vn.threeluaclmsapi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.threeluaclmsapi.dto.request.CreateSemesterRequest;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.dto.response.SemesterResponse;
import vn.threeluaclmsapi.service.SemesterService;

import java.util.List;

@RestController
@RequestMapping("/semester")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @PostMapping()
    public ResponseData<?> createSemester(@RequestBody CreateSemesterRequest request) {
        semesterService.createSemester(request);
        return new ResponseData<>(HttpStatus.CREATED.toString(), "Semester created successfully");
    }

    @GetMapping()
    public ResponseData<List<SemesterResponse>> getAllSemester(){
        List<SemesterResponse> list = semesterService.getAllSemester();
        return new ResponseData<>(HttpStatus.OK.toString(), "Semester list", list);
    }

}