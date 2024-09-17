package vn.threeluaclmsapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCourseRequest {
    private String subject;
    private String semester;
}
