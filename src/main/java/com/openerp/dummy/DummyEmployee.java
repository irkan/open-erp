package com.openerp.dummy;

import com.openerp.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DummyEmployee {

    public Employee getEmployee(Person person, List<Dictionary> positions, List<Organization> organizations){
        Employee employee = new Employee(
                person,
                DummyUtil.randomDictionary(positions),
                new Date(),
                null,
                DummyUtil.randomOrganization(organizations)
        );
        return employee;
    }
}
