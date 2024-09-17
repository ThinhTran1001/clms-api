package vn.threeluaclmsapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCourseRequest {

    @NotBlank(message = "Subject ID cannot be blank")
    private String subjectId;

    @NotBlank(message = "Semester ID cannot be blank")
    private String semesterId;

    private String classroomId;

    private String[] courseInstructorIds;
    private String[] enrollmentIds;
    private String[] lessonIds;
}
