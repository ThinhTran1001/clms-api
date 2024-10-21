package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_quiz_setting")
public class QuizSetting extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "show_review")
    private boolean showReview;

    @Column(name = "show_mark")
    private boolean showMark;

    @Column(name = "attempt_limit")
    private int attemptLimit;

    @Column(name = "is_random_question")
    private boolean isRandomQuestion;

    @Column(name = "time_limit")
    private int timeLimit;

    @Column(name = "save_draft")
    private boolean saveDraft;

}
