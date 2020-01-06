package com.openerp.specification.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


/**
 * [{
 * "type": "string",
 * "value": "***",
 * "field": "model"
 * },{
 * "type": "numeric",
 * "value": "***",
 * "field": "year",
 * "comparison": "gt"
 * }]
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter implements Specification {

    List<Condition> conditions;

    public Filter(String json) {
//        ObjectMapper mapper = new ObjectMapper();
//        this.conditions = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Condition.class));
    }

    public void addCondition(Condition condition) {
        this.conditions.add(condition);
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = buildPredicates(root, criteriaQuery, criteriaBuilder);
        return predicates.size() > 1
                ? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
                : predicates.get(0);
    }

    private List<Predicate> buildPredicates(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
//       return conditions.stream().map(this::buildPredicate).collect(toList());
        List<Predicate> predicates = new ArrayList<>();
        conditions.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
        return predicates;
    }

    public Predicate buildPredicate(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (condition.comparison) {
            case eq:
                return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case gt:
                break;
            case lt:
                break;
            case ne:
                return buildNotEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case isnull:
                return buildIsNullPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case in:
                break;
            default:
                return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
        }
        throw new RuntimeException();
    }

    private Predicate buildEqualsPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(condition.field), condition.value);
    }

    private Predicate buildNotEqualsPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.notEqual(root.get(condition.field), condition.value);
    }

    private Predicate buildIsNullPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isNull(root.get(condition.field));
    }
}