package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.dto.request.CreateClassroomRequest;
import vn.threeluaclmsapi.model.Classroom;

public interface ClassroomService {

    void createClassroom(CreateClassroomRequest request);
}
