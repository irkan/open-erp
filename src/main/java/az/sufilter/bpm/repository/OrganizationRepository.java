package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.Organization;
import az.sufilter.bpm.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    List<Organization> findOrganizationsByOrganizationIsNull();
}