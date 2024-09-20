package vn.threeluaclmsapi.dto.request.semester;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class CreateSemesterRequest implements Serializable {

    private String semesterName;

    private Date startDate;

    private Date endDate;
}
