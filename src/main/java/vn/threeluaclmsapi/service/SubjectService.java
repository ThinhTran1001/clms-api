package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.dto.request.subject.CreateSubjectRequest;
import vn.threeluaclmsapi.dto.request.subject.UpdateSubjectRequest;
import vn.threeluaclmsapi.model.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubjects();

    Subject getSubjectById(String courseId);

    Subject createSubject(CreateSubjectRequest request);

    Subject updateSubject(String id, UpdateSubjectRequest request);

    void updateSubjectStatus(String subjectId);

    List<Subject> listSubjectByCategoryId(String categoryId);
}
