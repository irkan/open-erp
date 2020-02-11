package com.openerp.domain;

import com.openerp.entity.Dictionary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ServiceDomain {
    private Dictionary dictionary;
    private Date nextServiceDate;

    public ServiceDomain(Dictionary dictionary, Date nextServiceDate) {
        this.dictionary = dictionary;
        this.nextServiceDate = nextServiceDate;
    }
}
