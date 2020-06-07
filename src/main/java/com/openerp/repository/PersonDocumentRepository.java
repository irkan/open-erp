package com.openerp.repository;

import com.openerp.entity.Dictionary;
import com.openerp.entity.Person;
import com.openerp.entity.PersonDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonDocumentRepository extends JpaRepository<PersonDocument, Integer> {
    List<PersonDocument> getPersonDocumentsByPersonAndDocumentType(Person person, Dictionary documentType);
}