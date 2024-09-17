package vn.threeluaclmsapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.threeluaclmsapi.dto.request.CreateClassroomRequest;
import vn.threeluaclmsapi.dto.response.ClassroomResponse;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.service.ClassroomService;

import java.util.List;

@RestController
@RequestMapping("/classroom")
@RequiredArgsConstructor
@Slf4j
public class ClassroomController {

    private final ClassroomService classroomService;

    @PostMapping()
    public ResponseData<?> createClassroom(@RequestBody @Valid CreateClassroomRequest request){
        classroomService.createClassroom(request);
        log.info("Classroom created");
        return new ResponseData<>(HttpStatus.CREATED.toString(), "Created classroom successfully!");
    }

    @GetMapping()
    public ResponseData<List<ClassroomResponse>> getAllClassrooms(){
        List<ClassroomResponse> classroomResponseList = classroomService.getAllClassroom();
        log.info("Classroom classroomResponseList");
        return new ResponseData<>(HttpStatus.OK.toString(), "Classroom list", classroomResponseList);
    }
}
