package vn.threeluaclmsapi.service;

import vn.threeluaclmsapi.dto.request.schedule.CreateScheduleRequest;
import vn.threeluaclmsapi.model.Schedule;

public interface ScheduleService {

    String createSchedule(CreateScheduleRequest request);

}
