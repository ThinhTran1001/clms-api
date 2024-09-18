package vn.threeluaclmsapi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import vn.threeluaclmsapi.util.constant.ValidationConstant;

import java.io.Serializable;

@Getter
public class CreateClassroomRequest implements Serializable {

    @NotBlank(message = "Tên lớp không được để trống!")
    @Pattern(regexp = "^[A-Z]{2}\\d{4}$", message = "Tên lớp không hợp lệ, phải có 2 chữ cái viết hoa và 4 số")
    private String classroomName;

    @Min(value = ValidationConstant.MIN_CLASSROOM_CAPACITY, message = "Lớp học cần có ít nhất 25 học sinh!")
    private Integer capacity;
}
