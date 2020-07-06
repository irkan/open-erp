package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "user_seq")
    @SequenceGenerator(sequenceName = "user_seq", allocationSize = 1, name = "user_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    //@UniqueElements(message = "Bu istifadəçi adı mövcuddur")
    @Pattern(regexp=".{5,20}",message="Minimum 5 maksimum 20 simvol ola bilər")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Pattern(regexp = "(.{6,96})", message="Minimum 6 maksimum 96 simvol ola bilər")
    @Column(name = "password", nullable = false)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    //@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    //@JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_changed_date")
    private Date lastChangedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_logged_date")
    private Date lastLoggedDate;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserModuleOperation> userModuleOperations;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_detail_id")
    private UserDetail userDetail;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private ApproverException approverException;

    //@UniqueElements(message = "Bu istifadəçi adı mövcuddur")
    //@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @ToString.Include()
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", unique = true, nullable = false)
    private Employee employee;

    public User(String username, String password, Employee employee, UserDetail userDetail) {
        this.username = username;
        this.password = password;
        this.employee = employee;
        this.userDetail = userDetail;
    }
}
