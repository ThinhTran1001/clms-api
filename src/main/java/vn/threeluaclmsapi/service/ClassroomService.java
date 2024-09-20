package vn.threeluaclmsapi.service;

import org.springframework.web.multipart.MultipartFile;
import vn.threeluaclmsapi.dto.request.classroom.CreateClassroomRequest;
import vn.threeluaclmsapi.dto.response.classroom.ClassroomDetailResponse;
import vn.threeluaclmsapi.dto.response.classroom.ClassroomResponse;

import java.util.List;

public interface ClassroomService {

    void createClassroom(CreateClassroomRequest request);

    List<ClassroomResponse> getAllClassroom();

    ClassroomDetailResponse getClassroomDetailById(String id);

    void importStudentListToClassroom(String classroomId, MultipartFile file);
}
