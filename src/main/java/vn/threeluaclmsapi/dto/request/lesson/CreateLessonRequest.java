package vn.threeluaclmsapi.dto.request.lesson;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateLessonRequest {

    @NotBlank(message = "Lesson title cannot be blank")
    private String lessonTitle;

    private String lessonDescription;

    private String courseId;

    private List<MultipartFile> attachments;
}
