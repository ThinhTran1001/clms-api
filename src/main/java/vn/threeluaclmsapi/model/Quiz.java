package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import vn.threeluaclmsapi.util.enums.QuizType;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tbl_quiz")

public class Quiz extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quiz_title")
    private String quizTitle;

    @Column(name = "quiz_description")
    private String quizDescription;

    @Column(name = "quiz_type")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private QuizType quizType;

    @Column(name = "status")
    private boolean status;

    @Column(name = "open_time")
    private Timestamp openTime;

    @Column(name = "close_time")
    private Timestamp closeTime;

    @OneToMany(mappedBy = "quiz")
    List<QuizAttempt> quizAttemptList;

    @ManyToMany
    @JoinTable(
            name = "tbl_quiz_question",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;
}
