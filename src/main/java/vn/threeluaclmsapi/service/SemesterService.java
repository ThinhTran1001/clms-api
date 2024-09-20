package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.dto.request.semester.CreateSemesterRequest;
import vn.threeluaclmsapi.dto.response.semester.SemesterResponse;

import java.util.List;

public interface SemesterService {

    void createSemester(CreateSemesterRequest request);

    List<SemesterResponse> getAllSemester();
}
