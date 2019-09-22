package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "admin_user_detail")
@Getter
@Setter
@NoArgsConstructor
public class UserDetail {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "email_notification", nullable = false, columnDefinition="boolean default true")
    private Boolean emailNotification = true;

    @Column(name = "sms_notification", nullable = false, columnDefinition="boolean default true")
    private Boolean smsNotification = false;

    @Column(name = "language")
    private String language;

    public UserDetail(String language) {
        this.language = language;
    }

    public UserDetail(Boolean emailNotification, Boolean smsNotification, String language) {
        this.emailNotification = emailNotification;
        this.smsNotification = smsNotification;
        this.language = language;
    }
}
