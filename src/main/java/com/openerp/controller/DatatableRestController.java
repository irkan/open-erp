package com.openerp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.openerp.datatable.CurrencyRateDatatable;
import com.openerp.datatable.EmployeeDatatable;
import com.openerp.datatable.UserDatatable;
import com.openerp.entity.CurrencyRate;
import com.openerp.entity.Employee;
import com.openerp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/datatable")
public class DatatableRestController extends SkeletonController {

    @Autowired
    private UserDatatable userDatatable;

    @Autowired
    private CurrencyRateDatatable currencyRateDatatable;

    @Autowired
    private EmployeeDatatable employeeDatatable;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public DataTablesOutput<User> getUsers(@Valid DataTablesInput input) {
        return userDatatable.findAll(input);
    }

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(value = "/currency-rate", method = RequestMethod.GET)
    public DataTablesOutput<CurrencyRate> getCurrencyRate(@Valid DataTablesInput input) {
        return currencyRateDatatable.findAll(input);
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public DataTablesOutput<Employee> getEmployee(@Valid DataTablesInput input) {
        return employeeDatatable.findAll(input);
    }

}