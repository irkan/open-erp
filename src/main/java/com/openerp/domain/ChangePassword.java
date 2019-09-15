package com.openerp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class ChangePassword {
    @Valid
    @Pattern(regexp = "(.{6,96})", message="Minimum 6 maksimum 96 simvol ola bilər")
    private String oldPassword;

    @Valid
    @Pattern(regexp = "(.{6,96})", message="Minimum 6 maksimum 96 simvol ola bilər")
    private String newPassword;

    @Valid
    @Pattern(regexp = "(.{6,96})", message="Minimum 6 maksimum 96 simvol ola bilər")
    private String verifyPassword;

}
