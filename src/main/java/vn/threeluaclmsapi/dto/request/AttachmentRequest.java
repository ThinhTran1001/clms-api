package vn.threeluaclmsapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentRequest {
    private String fileName;
    private byte[] fileContent;
}
