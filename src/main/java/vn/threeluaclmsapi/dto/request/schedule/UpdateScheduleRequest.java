package vn.threeluaclmsapi.dto.request.schedule;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UpdateScheduleRequest implements Serializable {

    private Integer slotId;

    private String scheduleDate;
}
