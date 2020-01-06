package com.openerp.datatable;

import com.openerp.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

public interface UserDatatable extends DataTablesRepository<User, Integer> {
    @Override
    DataTablesOutput<User> findAll(DataTablesInput dataTablesInput);
}