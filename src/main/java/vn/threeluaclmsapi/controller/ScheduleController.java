package vn.threeluaclmsapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.threeluaclmsapi.dto.request.schedule.CreateScheduleRequest;
import vn.threeluaclmsapi.dto.response.ResponseData;
import vn.threeluaclmsapi.dto.response.schedule.ScheduleResponse;
import vn.threeluaclmsapi.service.ScheduleService;

import javax.print.DocFlavor;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseData<String> createSchedule(@RequestBody CreateScheduleRequest request){
        String scheduleId = scheduleService.createSchedule(request);
        return new ResponseData<>(HttpStatus.CREATED.toString(), "Schedule created", scheduleId);
    }

    @GetMapping("/{classroomName}")
    public ResponseData<List<ScheduleResponse>> getScheduleListByClassroomName(@PathVariable String classroomName){
        List<ScheduleResponse> scheduleResponseList = scheduleService.getAllByClassroomName(classroomName);

        return new ResponseData<>(HttpStatus.OK.toString(), "Schedule list of classroom: "+ classroomName, scheduleResponseList);
    }
}
