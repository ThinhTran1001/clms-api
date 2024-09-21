package vn.threeluaclmsapi.dto.response.semester;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SemesterResponse implements Serializable {
    private String semesterName;

    private Date startDate;

    private Date endDate;

}
