package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "admin_log")
@Getter
@Setter
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_log_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_log_sequence", allocationSize = 1, name = "admin_log_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "type")
    private String type="info";

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "table_name")
    private String tableName;

    @Column(name = "row_id")
    private Integer rowId;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "operation")
    private String operation;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @Lob
    @Column(name = "encapsulate")
    private String encapsulate;

    @JsonIgnore
    @Lob
    @Column(name = "json")
    private String json;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "operation_date", nullable = false)
    private Date operationDate = new Date();

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Column(name = "operate_by", nullable = false)
    private String username;

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date operationDateFrom;


    @Transient
    private String encapsulateTransient;

    @Transient
    private String jsonTransient;

    public Log(@Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String tableName, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String operation, Integer rowId, String encapsulate, String username, String json) {
        this.tableName = tableName;
        this.operation = operation;
        this.rowId = rowId;
        this.encapsulate = encapsulate;
        this.username = username;
        this.json = json;
    }

    public Log(@Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String tableName, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String operation, Integer rowId, String encapsulate, String username, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description, String json) {
        this.tableName = tableName;
        this.operation = operation;
        this.rowId = rowId;
        this.encapsulate = encapsulate;
        this.username = username;
        this.description = description;
        this.json = json;
    }

    public Log(String type, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String tableName, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String operation, Integer rowId, String encapsulate, String username, String json) {
        this.type = type;
        this.tableName = tableName;
        this.operation = operation;
        this.rowId = rowId;
        this.encapsulate = encapsulate;
        this.username = username;
        this.json = json;
    }

    public Log(String type, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String tableName, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String operation, Integer rowId, String encapsulate, String username, String description, String json) {
        this.type = type;
        this.tableName = tableName;
        this.operation = operation;
        this.rowId = rowId;
        this.encapsulate = encapsulate;
        this.username = username;
        this.description = description;
        this.json = json;
    }

    public Log(@Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String operation, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description, String username) {
        this.operation = operation;
        this.description = description;
        this.username = username;
    }

    public Log(Date operationDate) {
        this.operationDate = operationDate;
    }

    public Log(Integer id, String encapsulateTransient, String jsonTransient) {
        this.id = id;
        this.encapsulateTransient = encapsulateTransient;
        this.jsonTransient = jsonTransient;
    }


}
