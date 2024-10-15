package vn.threeluaclmsapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.threeluaclmsapi.util.enums.Gender;
import vn.threeluaclmsapi.util.enums.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_user")
@Entity
public class User extends AbstractEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Gender gender;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name="status")
    private boolean status;

    @Column(name= "role")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student student;

    @OneToMany(mappedBy = "user")
    private List<Enrollment> studentEnrollments;

    @OneToMany(mappedBy = "user")
    private List<CourseInstructor> instructors;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
