package vn.threeluaclmsapi.service;

import org.springframework.web.multipart.MultipartFile;
import vn.threeluaclmsapi.dto.request.classroom.ClassroomRequest;
import vn.threeluaclmsapi.dto.response.classroom.ClassroomDetailResponse;
import vn.threeluaclmsapi.dto.response.classroom.ClassroomResponse;

import java.util.List;

public interface ClassroomService {

    void createClassroom(ClassroomRequest request);

    List<ClassroomResponse> getAllClassroom();

    ClassroomDetailResponse getClassroomDetailById(String id);

    void updateClassroom(ClassroomRequest request, String id);

    void changeStatus(String id);
}
