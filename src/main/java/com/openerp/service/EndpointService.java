package com.openerp.service;

import com.openerp.entity.Endpoint;
import com.openerp.entity.Vacation;
import com.openerp.repository.EndpointRepository;
import com.openerp.repository.VacationRepository;
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
public class EndpointService {
    @Autowired
    EndpointRepository endpointRepository;

    public Page<Endpoint> findAll(Endpoint endpoint, Pageable pageable){
        return endpointRepository.findAll(new Specification<Endpoint>() {
            @Override
            public Predicate toPredicate(Root<Endpoint> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(endpoint.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), endpoint.getId())));
                }
                if(endpoint.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), endpoint.getActive())));
                }
                if(endpoint.getConnectionType()!=null && endpoint.getConnectionType().getId()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("connectionType"), endpoint.getConnectionType().getId())));
                }
                if(endpoint.getDescription()!=null && !endpoint.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+endpoint.getDescription()+"%")));
                }
                if(endpoint.getHost()!=null && !endpoint.getHost().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("host"), "%"+endpoint.getHost()+"%")));
                }
                if(endpoint.getPort()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("port"), endpoint.getPort())));
                }
                if(endpoint.getUrl()!=null && !endpoint.getUrl().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("url"), "%"+endpoint.getUrl()+"%")));
                }
                if(endpoint.getLastStatusDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("lastStatusDate"), endpoint.getLastStatusDateFrom())));
                }
                if(endpoint.getLastStatusDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("lastStatusDate"), endpoint.getLastStatusDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
