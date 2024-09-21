package vn.threeluaclmsapi.dto.request.schedule;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CreateScheduleRequest implements Serializable {

    private String lessonId;

    private String classroomId;

    private Integer slotId;

    private String scheduleDate;

}
