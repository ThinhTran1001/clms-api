package vn.threeluaclmsapi.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ClassroomDetailResponse implements Serializable {

    private String classroomName;

    private Integer capacity;

    private List<StudentResponse> studentList;
}
