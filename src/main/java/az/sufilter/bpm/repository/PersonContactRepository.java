package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.Person;
import az.sufilter.bpm.entity.PersonContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonContactRepository extends JpaRepository<PersonContact, Integer> {
}