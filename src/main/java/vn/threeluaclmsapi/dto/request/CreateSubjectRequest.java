package vn.threeluaclmsapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubjectRequest {

    @NotBlank(message = "Subject name cannot be blank")
    private String subjectName;

    @NotBlank(message = "Subject code cannot be blank")
    @Pattern(regexp = "^[A-Z]{3}\\\\d{3}(?:[a-zA-Z])?$", message = "Mã môn không hợp lệ, phải có 3 ký tự in hoa, 3 ký tự số")
    private String subjectCode;

    private boolean status;

    private int credit;
}
