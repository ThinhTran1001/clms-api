package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.dto.request.CreateClassroomRequest;
import vn.threeluaclmsapi.dto.response.ClassroomDetailResponse;
import vn.threeluaclmsapi.dto.response.ClassroomResponse;
import vn.threeluaclmsapi.model.Classroom;

import java.util.List;

public interface ClassroomService {

    void createClassroom(CreateClassroomRequest request);

    List<ClassroomResponse> getAllClassroom();

    ClassroomDetailResponse getClassroomDetailById(String id);
}
