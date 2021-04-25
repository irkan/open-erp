package com.openerp.service;

import com.openerp.entity.Endpoint;
import com.openerp.entity.EndpointDetail;
import com.openerp.repository.EndpointDetailRepository;
import com.openerp.repository.EndpointRepository;
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
public class EndpointDetailService {
    @Autowired
    EndpointDetailRepository endpointDetailRepository;

    public Page<EndpointDetail> findAll(EndpointDetail endpointDetail, Pageable pageable){
        return endpointDetailRepository.findAll(new Specification<EndpointDetail>() {
            @Override
            public Predicate toPredicate(Root<EndpointDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(endpointDetail.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), endpointDetail.getId())));
                }
                if(endpointDetail.getEndpoint()!=null && endpointDetail.getEndpoint().getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("endpoint"), endpointDetail.getEndpoint().getId())));
                }
                if(endpointDetail.getEndpoint()!=null && endpointDetail.getEndpoint().getConnectionType()!=null && endpointDetail.getEndpoint().getConnectionType().getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("endpoint").get("connectionType"), endpointDetail.getEndpoint().getConnectionType().getId())));
                }
                if(endpointDetail.getDescription()!=null && !endpointDetail.getDescription().isEmpty()) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%"+endpointDetail.getDescription()+"%")));
                }
                if(endpointDetail.getDownDateFrom()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("downDate"), endpointDetail.getDownDateFrom())));
                }
                if(endpointDetail.getDownDate()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("downDate"), endpointDetail.getDownDate())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
