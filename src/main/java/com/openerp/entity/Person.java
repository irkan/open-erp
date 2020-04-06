package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openerp.util.Util;
import com.openerp.validation.EmployeeGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "common_person")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "common_sequence")
    @SequenceGenerator(sequenceName = "aa_common_sequence", allocationSize = 1, name = "common_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{2,20}",message="Minimum 2 maksimum 20 simvol ola bilər", groups = EmployeeGroup.class)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Pattern(regexp=".{2,20}",message="Minimum 2 maksimum 20 simvol ola bilər", groups = EmployeeGroup.class)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "father_name")
    private String fatherName;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthday;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_gender_id")
    private Dictionary gender;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_nationality_id")
    private Dictionary nationality;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_marital_status_id")
    private Dictionary maritalStatus;

    @Column(name = "id_card_pin_code")
    private String idCardPinCode;

    @Column(name = "id_card_serial_number")
    private String idCardSerialNumber;

    @Column(name = "voen")
    private String voen;

    @Column(name = "is_disability", nullable = false, columnDefinition="boolean default false")
    private Boolean disability = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade=CascadeType.ALL)
    private Customer customer;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade=CascadeType.ALL)
    private Employee employee;

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade=CascadeType.ALL)
    private Supplier supplier;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "common_contact_id")
    private Contact contact;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "person")
    private List<PersonDocument> personDocuments;

    public String getFullName() {
        return firstName+" "+lastName+" "+ Util.checkNull(fatherName);
    }

    public Person(Contact contact, String firstName, String lastName, String fatherName, Date birthday, Dictionary gender, Dictionary nationality, Dictionary maritalStatus, String idCardPinCode, String idCardSerialNumber, boolean disability) {
        this.contact = contact;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.birthday = birthday;
        this.gender = gender;
        this.nationality = nationality;
        this.maritalStatus = maritalStatus;
        this.idCardPinCode = idCardPinCode;
        this.idCardSerialNumber = idCardSerialNumber;
        this.disability = disability;
    }
}
