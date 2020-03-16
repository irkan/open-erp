package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "common_contact")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "common_sequence")
    @SequenceGenerator(sequenceName = "aa_common_sequence", allocationSize = 1, name = "common_sequence")
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

    @Email(message = "Doğru email ünvan daxil edin")
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "living_address")
    private String livingAddress;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_city_id")
    private Dictionary city;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_living_city_id")
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
