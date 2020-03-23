package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "idgroup_email_analyzer")
@Getter
@Setter
@NoArgsConstructor
public class EmailAnalyzer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "idgroup_email_analyzer_sequence")
    @SequenceGenerator(sequenceName = "aa_idgroup_email_analyzer_sequence", allocationSize = 1, name = "idgroup_email_analyzer_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "operation_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date operationDate = new Date();

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date operationDateFrom;

    @Column(name = "operation")
    private String operation;

    @Column(name = "queue_id")
    private String queueID;

    @Column(name = "service")
    private String service;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "from_")
    private String from;

    @Column(name = "to_")
    private String to;

    @Column(name = "size")
    private String size;

    @Column(name = "sender_host")
    private String senderHost;

    @Column(name = "remote_host")
    private String remoteHost;

    @Column(name = "user")
    private String user;

    @Column(name = "subject")
    private String subject;

    @Column(name = "fw_")
    private String fw;

    @Column(name = "re_")
    private String re;

    @Column(name = "r_")
    private String r;

    @Column(name = "msg_id")
    private String msgId;

    @Column(name = "result")
    private String result;

    @Column(name = "status")
    private String status;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;
}
