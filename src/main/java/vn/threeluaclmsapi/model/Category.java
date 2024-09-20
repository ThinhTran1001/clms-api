package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

import java.util.List;

import org.springframework.boot.context.properties.bind.DefaultValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_category")
@Entity
public class Category extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_status")
    private Boolean categoryStatus = true;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Subject> subjects;

}
