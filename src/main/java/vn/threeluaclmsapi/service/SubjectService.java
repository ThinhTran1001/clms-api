package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.model.Subject;

import java.util.List;

import org.hibernate.validator.constraints.UUID;

public interface SubjectService {
    List<Subject> getAllSubjects();

    Subject getSubjectById(String courseId);

    Subject createSubject(Subject subject);

    Subject updateSubject(String id, Subject subject);

    boolean deleteSubject(String id);
}
