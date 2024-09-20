package vn.threeluaclmsapi.service.impl;

import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.threeluaclmsapi.model.Subject;
import vn.threeluaclmsapi.repository.SubjectRepository;
import vn.threeluaclmsapi.service.SubjectService;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectById(String id) {
        Subject subject = subjectRepository.findById(id.toString())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        return subject;
    }

    @Override
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(String id, Subject subject) {
        if (subjectRepository.existsById(id)) {
            subject.setId(id);
            return subjectRepository.save(subject);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteSubject(String id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
