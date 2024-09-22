package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 255, message = "Category name must be between 1 and 255 characters")
    private String categoryName;

    @Column(name = "category_status")
    private Boolean status;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Subject> subjects;

}
