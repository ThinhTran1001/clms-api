package vn.threeluaclmsapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.threeluaclmsapi.dto.request.subject.CreateSubjectRequest;
import vn.threeluaclmsapi.dto.request.subject.UpdateSubjectRequest;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.model.Subject;
import vn.threeluaclmsapi.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseData<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        if (subjects.isEmpty()) {
            return new ResponseData<>("404", "No subjects found");
        } else {
            return new ResponseData<>("200", "Success", subjects);
        }
    }

    @PostMapping
    public ResponseData<Subject> createSubject(@Valid @RequestBody CreateSubjectRequest request) {
        Subject createdSubject = subjectService.createSubject(request);
        return new ResponseData<>("201", "Subject created successfully", createdSubject);
    }

    @GetMapping("/{id}")
    public ResponseData<Subject> getSubjectById(@PathVariable("id") String id) {
        Subject subject = subjectService.getSubjectById(id);
        if (subject != null) {
            return new ResponseData<>("200", "Subject found", subject);
        } else {
            return new ResponseData<>("404", "Subject not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseData<Subject> updateSubject(@PathVariable String id,
            @Valid @RequestBody UpdateSubjectRequest request) {
        Subject updatedSubject = subjectService.updateSubject(id, request);
        if (updatedSubject != null) {
            return new ResponseData<>("200", "Subject updated successfully", updatedSubject);
        } else {
            return new ResponseData<>("404", "Subject not found");
        }
    }

    @PutMapping("/{subjectId}/status")
    public ResponseData<String> updateSubjectStatus(@PathVariable String subjectId) {
        subjectService.updateSubjectStatus(subjectId);
        return new ResponseData<>("200", "Subject status updated successfully");
    }

}
