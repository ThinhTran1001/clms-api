package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_student")
@Entity
public class Student extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "student_code")
    private String studentCode;

    @Column(name = "major")
    private String major;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
