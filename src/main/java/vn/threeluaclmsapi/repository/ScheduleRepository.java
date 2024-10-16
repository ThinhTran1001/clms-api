package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.threeluaclmsapi.model.Schedule;


import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {


    @Query("SELECT c.classroomName, sub.subjectCode, sub.subjectName, s.slot.startTime, " +
            "s.slot.endTime, s.scheduleDate, s.instructor.user.fullName " +
            "FROM Schedule s " +
            "JOIN Lesson l ON s.lesson.id = l.id " +
            "JOIN Classroom c ON s.classroom.id = c.id " +
            "JOIN Course crs ON crs.id = l.course.id " +
            "JOIN Subject sub ON crs.subject.id = sub.id " +
            "WHERE c.classroomName LIKE %:classroomName%")
    List<Object[]> findAllByClassroomName(String classroomName);

    @Query("SELECT c.classroomName, sub.subjectCode, sub.subjectName, s.slot.startTime, " +
            "s.slot.endTime, s.scheduleDate, s.instructor.user.fullName " +
            "FROM Schedule s " +
            "JOIN Lesson l ON s.lesson.id = l.id " +
            "JOIN Classroom c ON s.classroom.id = c.id " +
            "JOIN Course crs ON crs.id = l.course.id " +
            "JOIN Subject sub ON crs.subject.id = sub.id " +
            "WHERE s.instructor.id = :instructorId")
    List<Object[]> findAllByInstructorId(String instructorId);

    @Query("SELECT c.classroomName, sub.subjectCode, sub.subjectName, s.slot.startTime, " +
            "s.slot.endTime, s.scheduleDate, s.instructor.user.fullName " +
            "FROM Schedule s " +
            "JOIN Lesson l ON s.lesson.id = l.id " +
            "JOIN Classroom c ON s.classroom.id = c.id " +
            "JOIN Course crs ON crs.id = l.course.id " +
            "JOIN Enrollment e ON e.course.id = crs.id " +
            "JOIN Subject sub ON crs.subject.id = sub.id " +
            "WHERE e.user.id = :studentId")
    List<Object[]> findAllByStudentId(String studentId);

    @Query("SELECT s " +
            "FROM Schedule s " +
            "WHERE s.scheduleDate = :scheduleDate AND s.slot.id = :slotId")
    Schedule findByScheduleDateAndSlot(LocalDate scheduleDate, Integer slotId);

    @Query("SELECT COUNT(s) " +
            "FROM Schedule s " +
            "WHERE s.classroom.id = :classroomId " +
            "AND s.scheduleDate BETWEEN :startOfWeek AND :endOfWeek")
    Long countSchedulesInWeek(String classroomId, LocalDate startOfWeek, LocalDate endOfWeek);

}
