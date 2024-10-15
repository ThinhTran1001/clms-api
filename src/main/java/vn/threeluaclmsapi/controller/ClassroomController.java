package vn.threeluaclmsapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.threeluaclmsapi.dto.request.classroom.ClassroomRequest;
import vn.threeluaclmsapi.dto.response.classroom.ClassroomDetailResponse;
import vn.threeluaclmsapi.dto.response.classroom.ClassroomResponse;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.service.ClassroomService;

import java.util.List;

@RestController
@RequestMapping("/classroom")
@RequiredArgsConstructor
@Slf4j
public class ClassroomController {

    private final ClassroomService classroomService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseData<?> createClassroom(@RequestBody @Valid ClassroomRequest request){
        classroomService.createClassroom(request);
        log.info("Classroom created");
        return new ResponseData<>(HttpStatus.CREATED.toString(), "Created classroom successfully!");
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ADMIN')")
    @GetMapping()
    public ResponseData<List<ClassroomResponse>> getAllClassrooms(){
        List<ClassroomResponse> classroomResponseList = classroomService.getAllClassroom();
        log.info("Classroom list");
        return new ResponseData<>(HttpStatus.OK.toString(), "Classroom list", classroomResponseList);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    @GetMapping("/{classroomId}")
    public ResponseData<ClassroomDetailResponse> getClassroomById(@PathVariable String classroomId){
        ClassroomDetailResponse classroomDetailResponse = classroomService.getClassroomDetailById(classroomId);
        log.info("Classroom detail with id: {}", classroomId);
        return new ResponseData<>(
                HttpStatus.OK.toString(),
                "Classroom detail with id: " + classroomId,
                classroomDetailResponse);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{classroomId}")
    public ResponseData<?> updateClassroom(@RequestBody @Valid ClassroomRequest request, @PathVariable String classroomId){
        classroomService.updateClassroom(request, classroomId);
        return new ResponseData<>(HttpStatus.OK.toString(), "Classroom updated successfully!");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/changeStatus/{classroomId}")
    public ResponseData<?> changeClassroomStatus(@PathVariable String classroomId){
        classroomService.changeStatus(classroomId);
        return new ResponseData<>(HttpStatus.OK.toString(), "Change classroom status successfully!");
    }

}
