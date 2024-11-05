package vn.threeluaclmsapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@Validated
@Tag(name = "Classroom Controller")
public class ClassroomController {

    private final ClassroomService classroomService;

    @Operation(method = "POST", summary = "Create new classroom", description = "Send a request via this API to create new classroom")
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
