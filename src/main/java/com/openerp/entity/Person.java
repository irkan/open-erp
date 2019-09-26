package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hr_person")
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{2,20}",message="Minimum 2 maksimum 20 simvol ola bilər")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Pattern(regexp=".{2,20}",message="Minimum 2 maksimum 20 simvol ola bilər")
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

    @Column(name = "id_card_pin_code")
    private String idCardPinCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    @JsonIgnore
    @OneToOne(mappedBy = "person")
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade=CascadeType.ALL)
    private Employee employee;

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade=CascadeType.ALL)
    private Supplier supplier;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "common_contact_id")
    private Contact contact;

    @JsonIgnore
    @OneToMany(mappedBy = "person")
    private List<PersonDocument> personDocuments;

    public String getFullName() {
        return firstName+" "+lastName+" "+fatherName;
    }

    public Person(Contact contact, String firstName, String lastName, String fatherName, Date birthday, Dictionary gender, Dictionary nationality, String idCardPinCode, User createdUser) {
        this.contact = contact;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.birthday = birthday;
        this.gender = gender;
        this.nationality = nationality;
        this.idCardPinCode = idCardPinCode;
        this.createdUser = createdUser;
    }
}
