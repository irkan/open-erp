package com.openerp.repository;

import com.openerp.entity.Person;
import com.openerp.entity.PersonDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDocumentRepository extends JpaRepository<PersonDocument, Integer> {
}