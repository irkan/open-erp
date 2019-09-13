package com.openerp.repository;

import com.openerp.entity.PersonContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonContactRepository extends JpaRepository<PersonContact, Integer> {
}