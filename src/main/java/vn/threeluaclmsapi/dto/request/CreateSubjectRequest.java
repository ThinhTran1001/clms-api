package vn.threeluaclmsapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubjectRequest {

    @NotBlank(message = "Subject name cannot be blank")
    private String subjectName;

    @NotBlank(message = "Subject code cannot be blank")
    private String subjectCode;

    private boolean status;

    private int credit;
}
