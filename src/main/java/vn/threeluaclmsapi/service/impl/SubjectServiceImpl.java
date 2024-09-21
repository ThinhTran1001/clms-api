package vn.threeluaclmsapi.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.threeluaclmsapi.model.Subject;
import vn.threeluaclmsapi.repository.SubjectRepository;
import vn.threeluaclmsapi.service.SubjectService;
import vn.threeluaclmsapi.dto.request.subject.CreateSubjectRequest;
import vn.threeluaclmsapi.dto.request.subject.UpdateSubjectRequest;
import vn.threeluaclmsapi.exception.CommonException;
import vn.threeluaclmsapi.exception.ResourceAlreadyExistsException;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAllSubjects() {
        try {
            return subjectRepository.findAll();
        } catch (Exception e) {
            log.error("Error while fetching all subjects: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch subjects", e);
        }
    }

    @Override
    public Subject getSubjectById(String id) {
        try {
            return subjectRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        } catch (Exception e) {
            log.error("Error while fetching subject by ID: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch subject", e);
        }
    }

    public Subject createSubject(CreateSubjectRequest request) {
        if (subjectRepository.existsBySubjectCode(request.getSubjectCode())) {
            throw new CommonException("Subject code must be unique");
        }

        Subject subject = new Subject();
        subject.setSubjectName(request.getSubjectName());
        subject.setSubjectCode(request.getSubjectCode());
        subject.setStatus(true);

        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(String id, UpdateSubjectRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        subject.setSubjectName(request.getSubjectName());
        subject.setSubjectCode(request.getSubjectCode());

        return subjectRepository.save(subject);
    }

    @Override
    public void updateSubjectStatus(String subjectId) {
        try {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

            subject.setStatus(!subject.isStatus());
            subjectRepository.save(subject);
        } catch (Exception e) {
            log.error("Error while updating subject status: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update subject status", e);
        }
    }
}
