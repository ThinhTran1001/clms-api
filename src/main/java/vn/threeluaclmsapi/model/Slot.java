package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_slot")
@Entity
public class Slot extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "slot_start_time")
    private Date startDate;

    @Column(name = "slot_end_time")
    private Date endDate;

    @OneToMany(mappedBy = "slot")
    private List<ClassroomSchedule> slotSchedules;

}
