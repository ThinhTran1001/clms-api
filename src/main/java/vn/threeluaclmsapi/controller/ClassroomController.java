package vn.threeluaclmsapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @PostMapping()
    public ResponseData<?> createClassroom(@RequestBody @Valid ClassroomRequest request){
        classroomService.createClassroom(request);
        log.info("Classroom created");
        return new ResponseData<>(HttpStatus.CREATED.toString(), "Created classroom successfully!");
    }

    @GetMapping()
    public ResponseData<List<ClassroomResponse>> getAllClassrooms(){
        List<ClassroomResponse> classroomResponseList = classroomService.getAllClassroom();
        log.info("Classroom list");
        return new ResponseData<>(HttpStatus.OK.toString(), "Classroom list", classroomResponseList);
    }

    @GetMapping("/{classroomId}")
    public ResponseData<ClassroomDetailResponse> getClassroomById(@PathVariable String classroomId){
        ClassroomDetailResponse classroomDetailResponse = classroomService.getClassroomDetailById(classroomId);
        log.info("Classroom detail with id: {}", classroomId);
        return new ResponseData<>(
                HttpStatus.OK.toString(),
                "Classroom detail with id: " + classroomId,
                classroomDetailResponse);
    }

    @PostMapping("/{classroomId}/import-student")
    public ResponseData<?> importStudentListToClassroom(
            @PathVariable String classroomId,
            @RequestParam("file") MultipartFile file){
        classroomService.importStudentListToClassroom(classroomId, file);
        return new ResponseData<>(
                HttpStatus.NO_CONTENT.toString(),
                "add student list to classroom successfully!");
    }

    @PutMapping("/{id}")
    public ResponseData<?> updateClassroom(@RequestBody ClassroomRequest request, @PathVariable String id){
        classroomService.updateClassroom(request, id);
        return new ResponseData<>(HttpStatus.OK.toString(), "Classroom updated successfully!");
    }

    @PutMapping("/{id}")
    public ResponseData<?> changeClassroomStatus(@PathVariable String id){
        classroomService.changeStatus(id);
        return new ResponseData<>(HttpStatus.OK.toString(), "Classroom updated successfully!");
    }
}
