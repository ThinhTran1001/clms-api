package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.threeluaclmsapi.dto.request.classroom.ClassroomRequest;
import vn.threeluaclmsapi.dto.response.classroom.ClassroomDetailResponse;
import vn.threeluaclmsapi.dto.response.classroom.ClassroomResponse;
import vn.threeluaclmsapi.dto.response.student.StudentResponse;
import vn.threeluaclmsapi.exception.CommonException;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.model.Classroom;
import vn.threeluaclmsapi.model.Student;
import vn.threeluaclmsapi.model.StudentClassroom;
import vn.threeluaclmsapi.model.User;
import vn.threeluaclmsapi.repository.ClassroomRepository;
import vn.threeluaclmsapi.repository.StudentClassroomRepository;
import vn.threeluaclmsapi.repository.StudentRepository;
import vn.threeluaclmsapi.repository.UserRepository;
import vn.threeluaclmsapi.service.ClassroomService;
import vn.threeluaclmsapi.util.common.DateUtils;
import vn.threeluaclmsapi.util.common.EnumUtils;
import vn.threeluaclmsapi.util.enums.Gender;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    private final StudentClassroomRepository studentClassroomRepository;

    @Override
    public void createClassroom(ClassroomRequest request) {
        if(classroomRepository.findByClassroomName(request.getClassroomName()).isPresent()) {
            throw new CommonException("Lớp này da ton tai!");
        }

        Classroom classroom = new Classroom();
        classroom.setClassroomName(request.getClassroomName());
        classroom.setCapacity(request.getCapacity());
        classroom.setStatus(true);

        classroomRepository.save(classroom);
    }

    @Override
    public List<ClassroomResponse> getAllClassroom() {
        List<Classroom> classrooms = classroomRepository.findAll();
        List<ClassroomResponse> listClassroomResponse = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            ClassroomResponse classroomResponse = new ClassroomResponse();
            classroomResponse.setClassroomName(classroom.getClassroomName());
            classroomResponse.setCapacity(classroom.getCapacity());
            listClassroomResponse.add(classroomResponse);
        }
        return listClassroomResponse;
    }

    @Override
    public ClassroomDetailResponse getClassroomDetailById(String classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy classroom với ID: " + classroomId));
        List<Object[]> studentListResults = classroomRepository.findStudentsByClassroomId(classroomId);

        ClassroomDetailResponse classroomDetailResponse = new ClassroomDetailResponse();
        classroomDetailResponse.setClassroomName(classroom.getClassroomName());
        classroomDetailResponse.setCapacity(classroom.getCapacity());

        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Object[] result : studentListResults) {
            String fullName = (String) result[0];
            String studentCode = (String) result[1];
            String major = (String) result[2];
            studentResponses.add(new StudentResponse(fullName, studentCode, major));
        }
        classroomDetailResponse.setStudentList(studentResponses);

        return classroomDetailResponse;
    }

    @Override
    public void importStudentListToClassroom(String classroomId, MultipartFile file) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy classroom với ID: " + classroomId));

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                // Get data from rows
                String fullName = getCellValueAsString(row.getCell(0));
                String email = getCellValueAsString(row.getCell(1));
                String address = getCellValueAsString(row.getCell(2));
                Gender gender = EnumUtils.parseGender(getCellValueAsString(row.getCell(3)));
                // convert to date string YYYY-MM-dd
                String dateOfBirth = DateUtils.convertDateFormatFromExcel(getCellValueAsString(row.getCell(4)));

                if(userRepository.findByEmail(email).isPresent()){
                    throw new CommonException("Email cannot duplicated!");
                }

                User user = createUserForImport(fullName, email, address, gender, dateOfBirth);

                String studentCode = getCellValueAsString(row.getCell(5));
                String major = getCellValueAsString(row.getCell(6));
                createStudentForImport(user, studentCode, major);

                createStudentClassroom(classroom, user);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi xử lý file Excel: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateClassroom(ClassroomRequest request, String id) {
        Classroom existedClassroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom with "+ id +" not found"));
        existedClassroom.setClassroomName(request.getClassroomName());
        existedClassroom.setCapacity(request.getCapacity());

        classroomRepository.save(existedClassroom);
    }

    @Override
    public void changeStatus(String id) {
        Classroom existedClassroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom with "+ id +" not found"));
        existedClassroom.setStatus(!existedClassroom.isStatus());

        classroomRepository.save(existedClassroom);
    }

    // Get value from Excel cell
    private String getCellValueAsString(Cell cell) {
        return cell != null ? cell.toString().trim() : "";
    }

    private User createUserForImport(String fullName, String email, String address, Gender gender, String dateOfBirth) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setAddress(address);
        user.setGender(gender);
        user.setDob(Date.valueOf(dateOfBirth));
        return userRepository.save(user);
    }

    private void createStudentForImport(User user, String studentCode, String major) {
        Student student = new Student();
        student.setUser(user);
        student.setStudentCode(studentCode);
        student.setMajor(major);
        studentRepository.save(student);
    }

    private void createStudentClassroom(Classroom classroom, User user) {
        StudentClassroom studentClassroom = new StudentClassroom();
        studentClassroom.setClassroom(classroom);
        studentClassroom.setUser(user);
        studentClassroomRepository.save(studentClassroom);
    }
}
