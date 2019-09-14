package com.openerp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class ChangePassword {
    @Pattern(regexp = "(.{6,96})", message="Minimum 6 maksimum 96 simvol ola bilər")
    private String oldPassword;
    @Pattern(regexp = "(.{6,96})", message="Minimum 6 maksimum 96 simvol ola bilər")
    private String newPassword;
    @Pattern(regexp = "(.{6,96})", message="Minimum 6 maksimum 96 simvol ola bilər")
    private String verifyPassword;
}
