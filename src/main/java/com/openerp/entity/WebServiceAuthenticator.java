package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "admin_web_service_authenticator")
@Getter
@Setter
@NoArgsConstructor
public class WebServiceAuthenticator {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Pattern(regexp=".{5,50}",message="Minimum 5 maksimum 50 simvol ola bilər")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Pattern(regexp=".{6,50}",message="Minimum 6 maksimum 50 simvol ola bilər")
    @Column(name = "password", nullable = false)
    private String password;

    @Pattern(regexp=".{0,250}",message="Maksimum 250 simvol ola bilər")
    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition="boolean default true")
    private Boolean active = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_admin_user_id")
    private User createdUser;

    public WebServiceAuthenticator(String username, String password, String description) {
        this.username = username;
        this.password = password;
        this.description = description;
    }

    public WebServiceAuthenticator(Boolean active) {
        this.username = username;
        this.password = password;
        this.description = description;
    }
}
