package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.threeluaclmsapi.dto.request.schedule.CreateScheduleRequest;
import vn.threeluaclmsapi.dto.request.schedule.UpdateScheduleRequest;
import vn.threeluaclmsapi.dto.response.schedule.ScheduleResponse;
import vn.threeluaclmsapi.exception.CommonException;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.model.*;
import vn.threeluaclmsapi.repository.*;
import vn.threeluaclmsapi.service.ScheduleService;
import vn.threeluaclmsapi.util.common.DateUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final LessonRepository lessonRepository;

    private final ClassroomRepository classroomRepository;

    private final SlotRepository slotRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public String createSchedule(CreateScheduleRequest request) {
        Classroom classroom = classroomRepository.findById(request.getClassroomId())
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found!"));

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found!"));

        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found!"));

        LocalDate scheduleDate = DateUtils.convertStringToLocalDate(request.getScheduleDate());

        if(scheduleRepository.findByScheduleDateAndSlot(scheduleDate, request.getSlotId()) != null){
            throw new CommonException("Classroom has already have a schedule in the same date!");
        }

        LocalDate startOfWeek = scheduleDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = scheduleDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        Long numberOfSchedulesInWeek = scheduleRepository.countSchedulesInWeek(
                request.getClassroomId(),
                startOfWeek,
                endOfWeek);

        if (numberOfSchedulesInWeek >= 2) {
            throw new CommonException("Classroom can only have 2 schedules in a week!");
        }

        Schedule schedule = Schedule.builder()
                .classroom(classroom)
                .lesson(lesson)
                .slot(slot)
                .scheduleDate(scheduleDate)
                .build();
        scheduleRepository.save(schedule);

        return schedule.getId();
    }

    @Override
    public List<ScheduleResponse> getAllByClassroomName(String classroomName) {
        List<Object[]> schedules = scheduleRepository.findAllByClassroomName(classroomName);
        return mapSchedulesToResponses(schedules);
    }

    @Override
    public List<ScheduleResponse> getAllByInstructor(String instructorId) {
        List<Object[]> schedules = scheduleRepository.findAllByInstructorId(instructorId);
        return mapSchedulesToResponses(schedules);
    }

    @Override
    public List<ScheduleResponse> getScheduleForStudent() {
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        }

        if (username == null) {
            throw new IllegalStateException("User is not authenticated");
        }

        User student = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find student"));

        List<Object[]> schedules = scheduleRepository.findAllByStudentId(student.getId());

        return mapSchedulesToResponses(schedules);
    }


    @Override
    @Transactional
    public void updateSchedule(UpdateScheduleRequest request, String id) {
        Schedule existedSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found!"));

        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found!"));

        LocalDate scheduleDate = DateUtils.convertStringToLocalDate(request.getScheduleDate());

        if(scheduleRepository.findByScheduleDateAndSlot(scheduleDate, request.getSlotId()) != null){
            throw new CommonException("Classroom has already have a schedule in the same date!");
        }

        existedSchedule.setSlot(slot);
        existedSchedule.setScheduleDate(scheduleDate);

        scheduleRepository.save(existedSchedule);
    }

    private ScheduleResponse mapToScheduleResponse(Object[] schedule) {
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setClassroomName((String) schedule[0]);
        scheduleResponse.setSubjectCode((String) schedule[1]);
        scheduleResponse.setSubjectName((String) schedule[2]);

        LocalTime startTime = (LocalTime) schedule[3];
        LocalTime endTime = (LocalTime) schedule[4];
        LocalDate scheduleDate = (LocalDate) schedule[5];
        String formattedSchedule = DateUtils.formatSchedule(
                startTime.toString(), endTime.toString(), scheduleDate.toString()
        );

        scheduleResponse.setScheduleDate(formattedSchedule);
        scheduleResponse.setInstructorName((String) schedule[6]);

        return scheduleResponse;
    }

    private List<ScheduleResponse> mapSchedulesToResponses(List<Object[]> schedules) {
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();
        for (Object[] schedule : schedules) {
            scheduleResponses.add(mapToScheduleResponse(schedule));
        }
        return scheduleResponses;
    }
}
