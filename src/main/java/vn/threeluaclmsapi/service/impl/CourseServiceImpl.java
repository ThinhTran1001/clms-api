package vn.threeluaclmsapi.service.impl;

import java.sql.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.threeluaclmsapi.dto.request.course.CreateCourseRequest;
import vn.threeluaclmsapi.dto.request.course.UpdateCourseRequest;
import vn.threeluaclmsapi.dto.response.CourseDetailsResponse;
import vn.threeluaclmsapi.dto.response.LessonDetailsResponse;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.model.*;
import vn.threeluaclmsapi.repository.*;
import vn.threeluaclmsapi.service.CourseService;
import vn.threeluaclmsapi.util.common.DateUtils;
import vn.threeluaclmsapi.util.common.EnumUtils;
import vn.threeluaclmsapi.util.enums.Gender;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final ClassroomRepository classroomRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<CourseDetailsResponse> listAllCourses() {
        try {
            List<Course> courses = courseRepository.findAll();

            return courses.stream().map(course -> {
                CourseDetailsResponse courseResponse = new CourseDetailsResponse();
                courseResponse.setCourseId(course.getId());
                courseResponse.setCourseTitle(course.getSubject().getSubjectName());
                courseResponse.setSubject(course.getSubject().getSubjectName());
                courseResponse.setSemester(course.getSemester().getSemesterName());
                return courseResponse;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while listing all courses: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to list courses", e);
        }
    }

    @Transactional
    @Override
    public void createCourse(CreateCourseRequest request) throws IOException {
        try {
            Subject subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

            Semester semester = semesterRepository.findById(UUID.fromString(request.getSemesterId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));

            Classroom classroom = classroomRepository.findById(request.getClassroomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));

            Course course = Course.builder()
                    .subject(subject)
                    .semester(semester)
                    .classroom(classroom)
                    .status(true)
                    .build();

            String baseDir = new File("").getAbsolutePath();
            String uploadDir = baseDir + File.separator + "uploads" + File.separator;
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
                List<Attachment> attachments = new ArrayList<>();

                for (MultipartFile file : request.getAttachments()) {
                    String uniqueFileName = UUID.randomUUID().toString() + "_"
                            + file.getOriginalFilename();
                    String filePath = uploadDir + uniqueFileName;

                    file.transferTo(new File(filePath));

                    Attachment attachment = Attachment.builder()
                            .fileName(uniqueFileName)
                            .filePath(filePath)
                            .course(course)
                            .build();

                    attachments.add(attachment);
                }

                course.setAttachments(attachments);
            }

            courseRepository.save(course);
        } catch (Exception e) {
            log.error("Error while creating course: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create course", e);
        }
    }

    @Override
    public CourseDetailsResponse viewCourse(UUID courseId) {
        try {
            Course course = courseRepository.findById(courseId.toString())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

            List<Lesson> lessons = lessonRepository.findByCourseId(courseId.toString());

            CourseDetailsResponse response = new CourseDetailsResponse();
            response.setCourseId(course.getId());
            response.setCourseTitle(course.getSubject().getSubjectName());
            response.setSubject(course.getSubject().getSubjectName());
            response.setSemester(course.getSemester().getSemesterName());

            List<LessonDetailsResponse> lessonDetails = lessons.stream()
                    .map(lesson -> new LessonDetailsResponse(lesson.getLessonTitle(),
                            lesson.getLessonDescription()))
                    .collect(Collectors.toList());

            response.setLessons(lessonDetails);
            return response;
        } catch (Exception e) {
            log.error("Error while viewing course: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to view course", e);
        }
    }

    @Override
    public void updateCourse(UUID courseId, UpdateCourseRequest request) {
        Course course = courseRepository.findById(courseId.toString())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Subject subject = subjectRepository.findById(request.getSubject())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        UUID semesterId = UUID.fromString(request.getSemester());
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        course.setSubject(subject);
        course.setSemester(semester);

        if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
            List<Attachment> attachments = new ArrayList<>();
            String uploadDir = new File("uploads").getAbsolutePath();

            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            for (MultipartFile file : request.getAttachments()) {
                String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                String filePath = uploadDir + uniqueFileName;
                try {
                    file.transferTo(new File(filePath));

                    Attachment attachment = Attachment.builder()
                            .fileName(uniqueFileName)
                            .filePath(filePath)
                            .course(course)
                            .build();

                    attachments.add(attachment);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to store file: " + e.getMessage());
                }
            }

            course.setAttachments(attachments);
        }

        courseRepository.save(course);
    }

    @Override
    public void updateCourseStatus(String courseId) {
        try {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

            course.setStatus(!course.isStatus());
            courseRepository.save(course);
        } catch (Exception e) {
            log.error("Error while updating course status: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update course status", e);
        }
    }

    @Override
    public void importStudentList(String courseId, MultipartFile file) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        try(Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
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

                User user = userRepository.findByEmail(email)
                        .orElseGet(() -> createUserForImport(fullName, email, address, gender, dateOfBirth));

                String studentCode = getCellValueAsString(row.getCell(5));
                String major = getCellValueAsString(row.getCell(6));
                createStudentForImport(user, studentCode, major);

                createEnrollment(user, course);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi xử lý file Excel: " + e.getMessage(), e);
        }
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
    }

    private void createEnrollment(User user, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setUser(user);
    }

}
