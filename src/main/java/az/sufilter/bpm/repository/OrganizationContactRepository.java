package az.sufilter.bpm.repository;

import az.sufilter.bpm.entity.Organization;
import az.sufilter.bpm.entity.OrganizationContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OrganizationContactRepository extends JpaRepository<OrganizationContact, Integer> {
}