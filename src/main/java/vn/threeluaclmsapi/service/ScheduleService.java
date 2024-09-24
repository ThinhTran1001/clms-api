package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.dto.request.schedule.CreateScheduleRequest;
import vn.threeluaclmsapi.dto.request.schedule.UpdateScheduleRequest;
import vn.threeluaclmsapi.dto.response.schedule.ScheduleResponse;
import vn.threeluaclmsapi.model.Schedule;

import java.util.List;

public interface ScheduleService {

    String createSchedule(CreateScheduleRequest request);

    List<ScheduleResponse> getAllByClassroomName(String classroomName);

    void updateSchedule(UpdateScheduleRequest request, String id);
}
