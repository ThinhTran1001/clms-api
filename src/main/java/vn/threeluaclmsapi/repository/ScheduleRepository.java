package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.threeluaclmsapi.model.Schedule;
import vn.threeluaclmsapi.model.Slot;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    @Query("SELECT c.classroomName, sub.subjectCode, sub.subjectName, s.slot.startTime, s.slot.endTime, s.scheduleDate " +
            "FROM Schedule s " +
            "JOIN Lesson l ON s.lesson.id = l.id " +
            "JOIN Classroom c ON s.classroom.id = c.id " +
            "JOIN Course crs ON crs.id = l.course.id " +
            "JOIN Subject sub ON crs.subject.id = sub.id " +
            "WHERE c.classroomName LIKE %:classroomName%")
    List<Object[]> findAllByClassroomName(String classroomName);

    Schedule findByScheduleDateAndSlot(LocalDate scheduleDate, Slot slot);

    @Query("SELECT COUNT(s) " +
            "FROM Schedule s " +
            "WHERE s.classroom.id = :classroomId " +
            "AND s.scheduleDate BETWEEN :startOfWeek AND :endOfWeek")
    Long countSchedulesInWeek(String classroomId, LocalDate startOfWeek, LocalDate endOfWeek);
}
