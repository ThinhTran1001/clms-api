package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_lesson")
@Entity
public class Lesson extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "lesson_title")
    private String lessonTitle;

    @Column(name = "lesson_description")
    private String lessonDescription;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "lesson")
    private List<ClassroomSchedule> lessonClassroomSchedules;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

}
