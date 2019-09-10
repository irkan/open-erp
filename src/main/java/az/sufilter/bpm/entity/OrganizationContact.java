package az.sufilter.bpm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "hr_organization_contact")
@Getter
@Setter
@NoArgsConstructor
public class OrganizationContact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_sequence", allocationSize = 1, name = "hr_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hr_organization_id")
    private Organization organization;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "home_phone")
    private String homePhone;

    @Email(message = "Doğru email ünvan daxil edin")
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_city_id")
    private Dictionary city;

    public OrganizationContact(Organization organization, String mobilePhone, String homePhone, String email, String address, Dictionary city) {
        this.organization = organization;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.email = email;
        this.address = address;
        this.city = city;
    }
}
