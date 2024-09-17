package vn.threeluaclmsapi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClassroomRequest {

    @NotBlank(message = "Classroom name cannot be blank")
    private String classroomName;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private Integer capacity;
}
