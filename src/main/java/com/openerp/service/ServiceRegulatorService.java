package com.openerp.service;

import com.openerp.entity.ServiceRegulator;
import com.openerp.entity.ServiceTask;
import com.openerp.repository.ServiceRegulatorRepository;
import com.openerp.repository.ServiceTaskRepository;
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
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
