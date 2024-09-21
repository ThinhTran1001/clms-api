package vn.threeluaclmsapi.dto.request.course;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UpdateCourseRequest {
    private String subject;
    private String semester;
    private String[] lessonIds;

    private List<MultipartFile> attachments;
}
