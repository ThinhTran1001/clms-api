package vn.threeluaclmsapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.threeluaclmsapi.dto.request.schedule.CreateScheduleRequest;
import vn.threeluaclmsapi.exception.ResourceNotFoundException;
import vn.threeluaclmsapi.model.Classroom;
import vn.threeluaclmsapi.model.Lesson;
import vn.threeluaclmsapi.model.Schedule;
import vn.threeluaclmsapi.model.Slot;
import vn.threeluaclmsapi.repository.ClassroomRepository;
import vn.threeluaclmsapi.repository.LessonRepository;
import vn.threeluaclmsapi.repository.ScheduleRepository;
import vn.threeluaclmsapi.repository.SlotRepository;
import vn.threeluaclmsapi.service.ScheduleService;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final LessonRepository lessonRepository;

    private final ClassroomRepository classroomRepository;

    private final SlotRepository slotRepository;

    @Override
    @Transactional
    public String createSchedule(CreateScheduleRequest request) {
        Classroom classroom = classroomRepository.findById(request.getClassroomId())
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));

        Schedule schedule = Schedule.builder()
                .classroom(classroom)
                .lesson(lesson)
                .slot(slot)
                .scheduleDate(request.getScheduleDate())
                .build();
        scheduleRepository.save(schedule);

        return schedule.getId();
    }
}
