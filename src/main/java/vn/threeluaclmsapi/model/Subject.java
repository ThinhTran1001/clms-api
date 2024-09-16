package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_subject")
@Entity
public class Subject extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "status")
    private boolean status;

    @Column(name = "credit")
    private int credit;

    @OneToMany(mappedBy = "subject")
    private List<Course> subjectCourses;

}
