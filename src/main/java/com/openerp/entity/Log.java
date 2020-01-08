package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private int rowId=0;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "operation")
    private String operation;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "encapsulate")
    private String encapsulate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "operation_date", nullable = false)
    private Date operationDate = new Date();

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Column(name = "operate_by", nullable = false)
    private String username;

    private Date operationDate2;

    public Log(@Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String tableName, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String operation, int rowId, String encapsulate, String username) {
        this.tableName = tableName;
        this.operation = operation;
        this.rowId = rowId;
        this.encapsulate = encapsulate;
        this.username = username;
    }

    public Log(@Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String tableName, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String operation, int rowId, String encapsulate, String username, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description) {
        this.tableName = tableName;
        this.operation = operation;
        this.rowId = rowId;
        this.encapsulate = encapsulate;
        this.username = username;
        this.description = description;
    }

    public Log(String type, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String tableName, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String operation, int rowId, String encapsulate, String username) {
        this.type = type;
        this.tableName = tableName;
        this.operation = operation;
        this.rowId = rowId;
        this.encapsulate = encapsulate;
        this.username = username;
    }

    public Log(@Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String operation, @Pattern(regexp = ".{0,250}", message = "Maksimum 250 simvol ola bilər") String description, String username) {
        this.operation = operation;
        this.description = description;
        this.username = username;
    }
}
