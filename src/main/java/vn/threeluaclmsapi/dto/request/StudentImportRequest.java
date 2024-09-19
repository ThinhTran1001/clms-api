package vn.threeluaclmsapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentImportRequest {

    private String classroomId;

    private String fileBase64;
}
