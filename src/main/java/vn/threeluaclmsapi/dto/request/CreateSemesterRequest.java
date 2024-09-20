package vn.threeluaclmsapi.dto.request;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class CreateSemesterRequest implements Serializable {

    private String semesterName;

    private Date startDate;

    private Date endDate;
}
