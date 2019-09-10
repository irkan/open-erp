package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.Person;
import az.sufilter.bpm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}