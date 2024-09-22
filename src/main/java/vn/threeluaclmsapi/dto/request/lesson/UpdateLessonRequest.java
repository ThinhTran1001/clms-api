package vn.threeluaclmsapi.dto.request.lesson;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UpdateLessonRequest {

    @NotBlank(message = "Lesson ID is required")
    private String lessonId;

    @NotBlank(message = "Lesson ID is required")
    private String lessonTitle;

    private String lessonDescription;

    private Boolean status;

    private String courseId;

    private List<MultipartFile> attachments;
}
