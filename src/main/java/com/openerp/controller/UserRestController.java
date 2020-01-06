package com.openerp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.openerp.datatable.UserDatatable;
import com.openerp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController

public class UserRestController extends SkeletonController {

    @Autowired
    private UserDatatable userDatatable;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(value = "/data/users", method = RequestMethod.GET)
    public DataTablesOutput<User> getUsers(@Valid DataTablesInput input) {
        return userDatatable.findAll(input);
    }
}