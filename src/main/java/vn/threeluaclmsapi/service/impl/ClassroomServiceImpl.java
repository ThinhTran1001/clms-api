package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.dto.request.classroom.ClassroomRequest;
import vn.threeluaclmsapi.dto.response.classroom.ClassroomDetailResponse;
import vn.threeluaclmsapi.dto.response.classroom.ClassroomResponse;
import vn.threeluaclmsapi.dto.response.student.StudentResponse;
import vn.threeluaclmsapi.exception.CommonException;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.model.Classroom;
import vn.threeluaclmsapi.repository.ClassroomRepository;
import vn.threeluaclmsapi.repository.StudentRepository;
import vn.threeluaclmsapi.repository.UserRepository;
import vn.threeluaclmsapi.service.ClassroomService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    @Override
    public void createClassroom(ClassroomRequest request) {
        if(classroomRepository.findByClassroomName(request.getClassroomName()).isPresent()) {
            throw new CommonException("Lớp này da ton tai!");
        }

        Classroom classroom = new Classroom();
        classroom.setClassroomName(request.getClassroomName());
        classroom.setCapacity(request.getCapacity());
        classroom.setStatus(true);

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
    public ClassroomDetailResponse getClassroomDetailById(String classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy classroom với ID: " + classroomId));

        ClassroomDetailResponse classroomDetailResponse = new ClassroomDetailResponse();
        classroomDetailResponse.setClassroomName(classroom.getClassroomName());
        classroomDetailResponse.setCapacity(classroom.getCapacity());

        return classroomDetailResponse;
    }

    @Override
    public void updateClassroom(ClassroomRequest request, String id) {
        Classroom existedClassroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom with "+ id +" not found"));
        existedClassroom.setClassroomName(request.getClassroomName());
        existedClassroom.setCapacity(request.getCapacity());

        classroomRepository.save(existedClassroom);
    }

    @Override
    public void changeStatus(String id) {
        Classroom existedClassroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom with "+ id +" not found"));
        existedClassroom.setStatus(!existedClassroom.isStatus());

        classroomRepository.save(existedClassroom);
    }


}
