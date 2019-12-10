package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "admin_notification")
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_notification_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_notification_sequence", allocationSize = 1, name = "admin_notification_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_notification_id")
    private Dictionary type;

    @Column(name = "message_from")
    private String from;

    @Pattern(regexp=".{4,250}",message="Minimum 4 maksimum 10 simvol ola bilər")
    @Column(name = "message_to")
    private String to;

    @Pattern(regexp=".{1,250}",message="Minimum 1 maksimum 250 simvol ola bilər")
    @Column(name = "subject")
    private String subject;

    @Pattern(regexp=".{1,1000}",message="Minimum 1 maksimum 1000 simvol ola bilər")
    @Column(name = "message")
    private String message;

    @Pattern(regexp=".{0,1000}",message="Maksimum 1000 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "is_send", nullable = false, columnDefinition="boolean default false")
    private Boolean sent = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sending_date", nullable = false)
    private Date sendingDate = new Date();

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public Notification(Dictionary type, String from, @Pattern(regexp = ".{4,250}", message = "Minimum 4 maksimum 10 simvol ola bilər") String to, @Pattern(regexp = ".{1,250}", message = "Minimum 1 maksimum 250 simvol ola bilər") String subject, @Pattern(regexp = ".{1,1000}", message = "Minimum 1 maksimum 1000 simvol ola bilər") String message, @Pattern(regexp = ".{1,1000}", message = "Minimum 1 maksimum 1000 simvol ola bilər") String description, String attachment) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
        this.description = description;
        this.attachment = attachment;
    }
}
