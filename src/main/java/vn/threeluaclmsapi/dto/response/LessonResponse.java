package vn.threeluaclmsapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonResponse {

    private String id;

    private String lessonTitle;

    private String lessonDescription;

    private Boolean status;

    private String courseId;

    private List<String> attachmentUrls;
}
