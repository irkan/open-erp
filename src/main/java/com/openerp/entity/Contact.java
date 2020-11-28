package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "contact_seq")
    @SequenceGenerator(sequenceName = "contact_seq", allocationSize = 1, name = "contact_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "relational_phone_number_1")
    private String relationalPhoneNumber1;

    @Column(name = "relational_phone_number_2")
    private String relationalPhoneNumber2;

    @Column(name = "relational_phone_number_3")
    private String relationalPhoneNumber3;

    @ToString.Exclude
    @Transient
    private String telephone;

    @Email(message = "Doğru email ünvan daxil edin")
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "living_address")
    private String livingAddress;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_city_id")
    private Dictionary city;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_living_city_id")
    private Dictionary livingCity;

    @Column(name = "geolocation")
    private String geolocation;

    public Contact(String mobilePhone, String homePhone, String email, String address, Dictionary city) {
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.email = email;
        this.address = address;
        this.city = city;
    }
}
