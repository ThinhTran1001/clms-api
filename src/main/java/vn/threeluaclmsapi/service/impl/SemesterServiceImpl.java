package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.dto.request.semester.CreateSemesterRequest;
import vn.threeluaclmsapi.dto.response.semester.SemesterResponse;
import vn.threeluaclmsapi.model.Semester;
import vn.threeluaclmsapi.repository.SemesterRepository;
import vn.threeluaclmsapi.service.SemesterService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;

    @Override
    public void createSemester(CreateSemesterRequest request) {
        Semester semester = Semester.builder()
                .semesterName(request.getSemesterName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        semesterRepository.save(semester);
    }

    @Override
    public List<SemesterResponse> getAllSemester() {
        List<Semester> semesterList = semesterRepository.findAll();
        List<SemesterResponse> semesterResponsesList = new ArrayList<>();

        for(Semester semester : semesterList) {
            SemesterResponse semesterResponse = new SemesterResponse();
            semesterResponse.setSemesterName(semester.getSemesterName());
            semesterResponse.setStartDate(semester.getStartDate());
            semesterResponse.setEndDate(semester.getEndDate());
            semesterResponsesList.add(semesterResponse);
        }

        return semesterResponsesList;
    }

}
