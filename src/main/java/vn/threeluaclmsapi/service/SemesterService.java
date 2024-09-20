package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.dto.request.CreateSemesterRequest;
import vn.threeluaclmsapi.dto.response.SemesterResponse;

import java.util.List;

public interface SemesterService {

    void createSemester(CreateSemesterRequest request);

    List<SemesterResponse> getAllSemester();
}
