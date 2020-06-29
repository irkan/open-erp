package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "migration_user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MigrationUser {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "migration_user_seq")
    @SequenceGenerator(sequenceName = "migration_user_seq", allocationSize = 1, name = "migration_user_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "is_administrator")
    private Integer isAdministrator;

    @Column(name = "email_notification")
    private Integer emailNotification;

    @Column(name = "language")
    private String language;

    @Column(name = "sms_notification")
    private Integer smsNotification;

    @Column(name = "paginationSize")
    private Integer paginationSize;

    @Column(name = "organization")
    private String organization;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "father_name")
    private String fatherName;
}