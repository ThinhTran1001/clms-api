package vn.threeluaclmsapi.dto.response;


import lombok.*;
import vn.threeluaclmsapi.util.enums.Gender;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;

    private String email;

    private Date dob;

    private Gender gender;

    private String fullName;

    private String address;

    private boolean status;
}

