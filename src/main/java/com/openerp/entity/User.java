package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "admin_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserModuleOperation> userModuleOperations;

    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL)
    private UserDetail userDetail;

    //@UniqueElements(message = "Bu istifadəçi adı mövcuddur")
    //@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_employee_id", unique = true, nullable = false)
    private Employee employee;

    @OneToOne(mappedBy = "createdUser")
    private Person createdUser;

    public User(String username, String password, Employee employee) {
        this.username = username;
        this.password = password;
        this.employee = employee;
    }
}
