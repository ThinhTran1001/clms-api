package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.dto.request.CreateClassroomRequest;
import vn.threeluaclmsapi.dto.response.ClassroomDetailResponse;
import vn.threeluaclmsapi.dto.response.ClassroomResponse;
import vn.threeluaclmsapi.dto.response.StudentResponse;
import vn.threeluaclmsapi.exception.CommonException;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.model.Classroom;
import vn.threeluaclmsapi.repository.ClassroomRepository;
import vn.threeluaclmsapi.service.ClassroomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    @Override
    public void createClassroom(CreateClassroomRequest request) {
        if(classroomRepository.findByClassroomName(request.getClassroomName()).isPresent()) {
            throw new CommonException("Lớp này da ton tai!");
        }

        Classroom classroom = new Classroom();
        classroom.setClassroomName(request.getClassroomName());
        classroom.setCapacity(request.getCapacity());

        classroomRepository.save(classroom);
    }

    @Override
    public List<ClassroomResponse> getAllClassroom() {
        List<Classroom> classrooms = classroomRepository.findAll();
        List<ClassroomResponse> listClassroomResponse = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            ClassroomResponse classroomResponse = new ClassroomResponse();
            classroomResponse.setClassroomName(classroom.getClassroomName());
            classroomResponse.setCapacity(classroom.getCapacity());
            listClassroomResponse.add(classroomResponse);
        }
        return listClassroomResponse;
    }

    @Override
    public ClassroomDetailResponse getClassroomDetailById(String id) {
        Optional<Classroom> classroom = classroomRepository.findById(id);
        List<Object[]> studentListResults = classroomRepository.findStudentsByClassroomId(id);

        if (classroom.isEmpty()) {
            throw new ResourceNotFoundException("Khong tim thay classroom with id: " + id);
        }

        ClassroomDetailResponse classroomDetailResponse = new ClassroomDetailResponse();
        classroomDetailResponse.setClassroomName(classroom.get().getClassroomName());
        classroomDetailResponse.setCapacity(classroom.get().getCapacity());

        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Object[] result : studentListResults) {
            String fullName = (String) result[0];
            String studentCode = (String) result[1];
            String major = (String) result[2];

            studentResponses.add(new StudentResponse(fullName, studentCode, major));
        }
        classroomDetailResponse.setStudentList(studentResponses);

        return classroomDetailResponse;
    }
}
