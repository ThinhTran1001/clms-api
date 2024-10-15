package vn.threeluaclmsapi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.threeluaclmsapi.dto.request.semester.CreateSemesterRequest;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.dto.response.semester.SemesterResponse;
import vn.threeluaclmsapi.service.SemesterService;

import java.util.List;

@RestController
@RequestMapping("/semester")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseData<?> createSemester(@RequestBody CreateSemesterRequest request) {
        semesterService.createSemester(request);
        return new ResponseData<>(HttpStatus.CREATED.toString(), "Semester created successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseData<List<SemesterResponse>> getAllSemester(){
        List<SemesterResponse> list = semesterService.getAllSemester();
        return new ResponseData<>(HttpStatus.OK.toString(), "Semester list", list);
    }

}
