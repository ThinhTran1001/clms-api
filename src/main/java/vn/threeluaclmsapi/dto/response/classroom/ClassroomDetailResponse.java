package vn.threeluaclmsapi.dto.response.classroom;

import lombok.Getter;
import lombok.Setter;
import vn.threeluaclmsapi.dto.response.student.StudentResponse;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ClassroomDetailResponse implements Serializable {

    private String classroomName;

    private Integer capacity;

    private List<StudentResponse> studentList;
}
