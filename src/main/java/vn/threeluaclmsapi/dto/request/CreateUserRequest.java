package vn.threeluaclmsapi.dto.request;


import lombok.Getter;
import lombok.Setter;
import vn.threeluaclmsapi.util.enums.Gender;

import java.util.Date;

@Getter
@Setter
public class UserRequest {
    private String username;

    private String password;

    private String email;

    private Date dob;

    private Gender gender;

    private String fullName;

    private String address;

    private boolean status;
}
