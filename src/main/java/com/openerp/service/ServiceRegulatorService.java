package com.openerp.service;

import com.openerp.entity.ServiceRegulator;
import com.openerp.repository.ServiceRegulatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceRegulatorService {
    @Autowired
    ServiceRegulatorRepository serviceRegulatorRepository;

    public Page<ServiceRegulator> findAll(ServiceRegulator serviceRegulator, Pageable pageable){
        return serviceRegulatorRepository.findAll(new Specification<ServiceRegulator>() {
            @Override
            public Predicate toPredicate(Root<ServiceRegulator> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(serviceRegulator.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), serviceRegulator.getId())));
                }
                if(serviceRegulator.getSales()!=null && serviceRegulator.getSales().getNotServiceNext()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("sales").get("notServiceNext"), serviceRegulator.getSales().getNotServiceNext())));
                }
                if(serviceRegulator.getSales()!=null && serviceRegulator.getSales().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("sales"), serviceRegulator.getSales().getId())));
                }
                if(serviceRegulator.getSales()!=null && serviceRegulator.getSales().getOrganization()!=null && serviceRegulator.getSales().getOrganization().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("sales").get("organization"), serviceRegulator.getSales().getOrganization().getId())));
                }
                if(serviceRegulator.getSales()!=null && serviceRegulator.getServiceNotification()!=null && serviceRegulator.getServiceNotification().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("serviceNotification"), serviceRegulator.getServiceNotification().getId())));
                }
                if(serviceRegulator.getServicedDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("servicedDate"), serviceRegulator.getServicedDateFrom())));
                }
                if(serviceRegulator.getServicedDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("servicedDate"), serviceRegulator.getServicedDate())));
                }
                if(serviceRegulator.getServicedDateFrom()!=null && serviceRegulator.getServicedDate()==null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("servicedDate"), serviceRegulator.getServicedDateFrom())));
                }
                if(serviceRegulator.getServicedDateFrom()==null && serviceRegulator.getServicedDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("servicedDate"), serviceRegulator.getServicedDate())));
                }
                if(serviceRegulator.getSales()!=null && serviceRegulator.getSales().getCustomer()!=null && serviceRegulator.getSales().getCustomer().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("sales").get("customer"), serviceRegulator.getSales().getCustomer().getId())));
                }
                if(serviceRegulator.getSales()!=null && serviceRegulator.getSales().getCustomer()!=null && serviceRegulator.getSales()!=null && serviceRegulator.getSales().getCustomer().getPerson()!=null && serviceRegulator.getSales()!=null && serviceRegulator.getSales().getCustomer().getPerson().getFirstName()!=null && !serviceRegulator.getSales().getCustomer().getPerson().getFirstName().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("sales").get("customer").get("person").get("firstName"), "%"+serviceRegulator.getSales().getCustomer().getPerson().getFirstName()+"%")));
                }
                if(serviceRegulator.getSales()!=null && serviceRegulator.getSales().getCustomer()!=null && serviceRegulator.getSales()!=null && serviceRegulator.getSales().getCustomer().getPerson()!=null && serviceRegulator.getSales()!=null && serviceRegulator.getSales().getCustomer().getPerson().getLastName()!=null && !serviceRegulator.getSales().getCustomer().getPerson().getLastName().isEmpty()){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("sales").get("customer").get("person").get("lastName"), "%"+serviceRegulator.getSales().getCustomer().getPerson().getLastName()+"%")));
                }
                if(serviceRegulator.getSales()!=null &&
                        serviceRegulator.getSales().getCustomer()!=null &&
                        serviceRegulator.getSales().getCustomer().getPerson()!=null &&
                        serviceRegulator.getSales().getCustomer().getPerson().getContact()!=null &&
                        serviceRegulator.getSales().getCustomer().getPerson().getContact().getTelephone()!=null &&
                        !serviceRegulator.getSales().getCustomer().getPerson().getContact().getTelephone().isEmpty()){
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(root.get("sales").get("customer").get("person").get("contact").get("mobilePhone"), "%"+serviceRegulator.getSales().getCustomer().getPerson().getContact().getTelephone()+"%"),
                            criteriaBuilder.like(root.get("sales").get("customer").get("person").get("contact").get("homePhone"), "%"+serviceRegulator.getSales().getCustomer().getPerson().getContact().getTelephone()+"%"),
                            criteriaBuilder.like(root.get("sales").get("customer").get("person").get("contact").get("relationalPhoneNumber1"), "%"+serviceRegulator.getSales().getCustomer().getPerson().getContact().getTelephone()+"%"),
                            criteriaBuilder.like(root.get("sales").get("customer").get("person").get("contact").get("relationalPhoneNumber2"), "%"+serviceRegulator.getSales().getCustomer().getPerson().getContact().getTelephone()+"%"),
                            criteriaBuilder.like(root.get("sales").get("customer").get("person").get("contact").get("relationalPhoneNumber3"), "%"+serviceRegulator.getSales().getCustomer().getPerson().getContact().getTelephone()+"%")
                    ));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
