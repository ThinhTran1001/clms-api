package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.dto.request.CreateClassroomRequest;
import vn.threeluaclmsapi.model.Classroom;
import vn.threeluaclmsapi.repository.ClassroomRepository;
import vn.threeluaclmsapi.service.ClassroomService;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    @Override
    public void createClassroom(CreateClassroomRequest request) {
        Classroom classroom = Classroom.builder()
                .classroomName(request.getClassroomName())
                .capacity(request.getCapacity())
                .build();
        classroomRepository.save(classroom);
    }
}
