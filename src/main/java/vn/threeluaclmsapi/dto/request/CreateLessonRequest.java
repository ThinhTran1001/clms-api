package vn.threeluaclmsapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLessonRequest {

    @NotBlank(message = "Lesson title cannot be blank")
    private String lessonTitle;

    private String lessonDescription;

    private String courseId;
}
