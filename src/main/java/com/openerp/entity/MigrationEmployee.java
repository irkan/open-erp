package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "migration_employee")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MigrationEmployee {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "migration_employee_seq")
    @SequenceGenerator(sequenceName = "migration_employee_seq", allocationSize = 1, name = "migration_employee_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "organization")
    private String organization;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "position")
    private String position;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_card_number")
    private String bankCardNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "contract_start_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date contractStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "contract_end_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date contractEndDate;

    @Column(name = "description")
    private String description;

    @Column(name = "is_specialist_or_manager")
    private Integer isSpecialistOrManager;

    @Column(name = "leave_reason")
    private String leaveReason;

    @Column(name = "social_card_number")
    private String socialCardNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "father_name")
    private String fatherName;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthday;

    @Column(name = "id_card_serial_number")
    private String idCardSerialNumber;

    @Column(name = "id_card_pin_code")
    private String idCardPinCode;

    @Column(name = "is_disability")
    private Integer isDisability;

    @Column(name = "voen")
    private String voen;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "marital")
    private String marital;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "geolocation")
    private String geolocation;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;
}