package vn.threeluaclmsapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.threeluaclmsapi.dto.request.CreateClassroomRequest;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.service.ClassroomService;

@RestController
@RequestMapping("/classroom")
@RequiredArgsConstructor
@Slf4j
public class ClassroomController {

    private final ClassroomService classroomService;

    @PostMapping()
    public ResponseData<?> createClassroom(@RequestBody CreateClassroomRequest request){
        classroomService.createClassroom(request);
        log.info("Classroom created");
        return new ResponseData<>(HttpStatus.CREATED.toString(), "Created classroom successfully!");
    }
}
