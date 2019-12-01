package com.openerp.dummy;

import com.openerp.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DummyEmployee {

    public Employee getEmployee(Person person, List<Dictionary> positions, List<Organization> organizations){
        Dictionary position = DummyUtil.randomDictionary(positions);
        if(position.getAttr1().equalsIgnoreCase("Consul")){
            person.setVoen(DummyUtil.randomVoen());
        }
        Employee employee = new Employee(
                person,
                DummyUtil.randomDictionary(positions),
                DummyUtil.randomContractStartDate(),
                null,
                DummyUtil.randomOrganization(organizations),
                DummyUtil.randomSocialCardNumber(),
                DummyUtil.randomBankAccountNumber(),
                DummyUtil.randomBankCardNumber(),
                DummyUtil.randomBoolean(),
                ""
        );
        return employee;
    }
}
