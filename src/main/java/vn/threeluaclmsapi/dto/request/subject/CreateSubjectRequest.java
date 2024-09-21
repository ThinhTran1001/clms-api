package vn.threeluaclmsapi.dto.request.subject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubjectRequest {

    @NotBlank(message = "Subject name cannot be blank")
    private String subjectName;

    @NotBlank(message = "Subject code cannot be blank")
    @Size(min = 6, max = 7, message = "Subject name must be 6 characters long")
    @Pattern(regexp = "^[A-Z]{3}\\d{3}([a-zA-Z])?$", message = "Môn không hợp lệ, phải có 3 ký tự in hoa, 3 ký tự số và có thể có 1 ký tự chữ cái ở cuối")
    private String subjectCode;

    private int credit;
}
