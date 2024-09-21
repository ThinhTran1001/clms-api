package vn.threeluaclmsapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.threeluaclmsapi.dto.request.schedule.CreateScheduleRequest;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.service.ScheduleService;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseData<String> createSchedule(CreateScheduleRequest request){
        String scheduleId = scheduleService.createSchedule(request);
        return new ResponseData<>(HttpStatus.CREATED.toString(), "Schedule created", scheduleId);
    }

}
