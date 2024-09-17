package vn.threeluaclmsapi.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDetailsResponse {
    private String courseId;
    private String courseTitle;
    private String subject;
    private String semester;
    private List<LessonDetailsResponse> lessons;

}