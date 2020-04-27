package com.openerp.domain;

import com.openerp.entity.Dictionary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    private Dictionary city;
    private String address;
}
