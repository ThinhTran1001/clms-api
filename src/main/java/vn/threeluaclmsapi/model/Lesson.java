package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(name = "status")
    private Boolean status;

    @Column(name = "lesson_title")
    private String lessonTitle;

    @Column(name = "lesson_description")
    private String lessonDescription;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "lesson")
    private List<Schedule> lessonSchedules;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

}
