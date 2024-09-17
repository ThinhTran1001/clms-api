package vn.threeluaclmsapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LessonDetailsResponse {
    private String lessonTitle;
    private String lessonDescription;
}