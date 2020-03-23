package com.openerp.service;

import com.openerp.entity.EmailAnalyzer;
import com.openerp.entity.Vacation;
import com.openerp.repository.EmailAnalyzerRepository;
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
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailAnalyzerService {
    @Autowired
    EmailAnalyzerRepository emailAnalyzerRepository;

    public Page<EmailAnalyzer> findAll(EmailAnalyzer emailAnalyzer, Pageable pageable){
        return emailAnalyzerRepository.findAll(new Specification<EmailAnalyzer>() {
            @Override
            public Predicate toPredicate(Root<EmailAnalyzer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(emailAnalyzer.getId()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), emailAnalyzer.getId())));
                }
                if(emailAnalyzer.getActive()!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), emailAnalyzer.getActive())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }
}
