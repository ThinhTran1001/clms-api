package vn.threeluaclmsapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Setter
@Getter
public class StudentResponse implements Serializable {

    private String studentCode;

    private String studentName;

    private String major;

}
