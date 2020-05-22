package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "admin_migration")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Migration {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_migration_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_migration_sequence", allocationSize = 1, name = "admin_migration_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @Column(name = "operation_type")
    private String operationType;  //Satış - S    Qaytarılma - Q

    @Column(name = "file_name")
    private String fileName;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    @Column(name = "upload_date", nullable = false)
    private Date uploadDate = new Date();

    @Lob
    @Column(name = "file_content")
    private byte[] fileContent;

    @Transient
    private Integer dataCount;

    @Transient
    private Integer insertedCount;

    @Transient
    private Integer processedCount;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Column(name = "status", nullable = false)
    private Integer status = 0;  //1 - yuklendi  2 - xetali fayl

    @Lob
    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "migration", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MigrationDetail> migrationDetails;
}
