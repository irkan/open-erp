package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDetail {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "user_detail_seq")
    @SequenceGenerator(sequenceName = "user_detail_seq", allocationSize = 1, name = "user_detail_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "email_notification", nullable = false, columnDefinition="boolean default true")
    private Boolean emailNotification = true;

    @Column(name = "sms_notification", nullable = false, columnDefinition="boolean default true")
    private Boolean smsNotification = false;

    @Column(name = "language")
    private String language;

    @Column(name = "pagination_size")
    private Integer paginationSize = 100;

    @Column(name = "is_administrator", nullable = false, columnDefinition="boolean default false")
    private Boolean administrator = false;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "start_module_id")
    private Module startModule;

    public UserDetail(String language) {
        this.language = language;
    }

    public UserDetail(Boolean emailNotification, Boolean smsNotification, String language) {
        this.emailNotification = emailNotification;
        this.smsNotification = smsNotification;
        this.language = language;
    }

    public UserDetail(String language, Boolean administrator) {
        this.language = language;
        this.administrator = administrator;
    }
}
