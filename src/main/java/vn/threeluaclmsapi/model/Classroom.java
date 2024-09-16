package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import org.hibernate.mapping.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_classroom")
@Entity
public class Classroom extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "classroom_name")
    private String classroomName;

    @Column(name = "capacity")
    private int capacity;

    @OneToMany(mappedBy = "classroom")
    private List<StudentClassroom> classroomStudents;

    @OneToMany(mappedBy = "classroom")
    private List<ClassroomSchedule> classroomSchedules;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;

}