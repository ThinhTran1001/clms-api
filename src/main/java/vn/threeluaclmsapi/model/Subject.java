package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotBlank(message = "Subject name cannot be blank")
    @Size(min = 6, message = "Subject name must be at least 6 characters long")
    @Column(name = "subject_name")
    private String subjectName;

    @NotBlank(message = "Subject code cannot be blank")
    @Column(name = "subject_code", unique = true)
    private String subjectCode;

    @Column(name = "status")
    private boolean status;

    @Column(name = "credit")
    private int credit;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
