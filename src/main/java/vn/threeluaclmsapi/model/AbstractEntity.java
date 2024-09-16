package vn.threeluaclmsapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

@Getter
@MappedSuperclass
public abstract class AbstractEntity {

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdDate;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String lastModifiedBy;

    @UpdateTimestamp
    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp lastModifiedDate;
}
