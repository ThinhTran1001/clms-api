package vn.threeluaclmsapi.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import vn.threeluaclmsapi.util.enums.Gender;

import java.util.Date;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "Tên tài khoản không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@fpt.edu.vn$",
            message = "Tên tài khoản phải là email và thuộc miền @fpt.edu.vn")
    private String email;

    private String password;

    private Date dob;

    private Gender gender;

    private String fullName;

    private String address;

    private boolean status;
}
