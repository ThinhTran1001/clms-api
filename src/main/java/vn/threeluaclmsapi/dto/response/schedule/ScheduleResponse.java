package vn.threeluaclmsapi.dto.response.schedule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@Setter
@Getter
public class ScheduleResponse implements Serializable {

    private String classroomName;

    private String subjectCode;

    private String subjectName;

    private String scheduleDate;


}
