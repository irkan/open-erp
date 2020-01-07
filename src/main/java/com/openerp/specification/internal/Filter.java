package com.openerp.specification.internal;

import com.openerp.util.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.*;
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
public class Filter implements Specification {

    List<Condition> conditions;

    public Filter(String json) {
//        ObjectMapper mapper = new ObjectMapper();
//        this.conditions = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Condition.class));
    }

    public Filter() {
        conditions = new ArrayList<>();
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
            case like:
                return buildLikePredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case gt:
                return buildGtPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case lt:
                return buildLtPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
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

    private Predicate buildGtPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.gt(root.get(condition.field), Float.parseFloat(String.valueOf(condition.value)));
    }

    private Predicate buildLtPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lt(root.get(condition.field), Float.parseFloat(String.valueOf(condition.value)));
    }

    private Predicate buildLikePredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(condition.field),  "%" + condition.value + "%");
    }

    private Predicate buildNotEqualsPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.notEqual(root.get(condition.field), condition.value);
    }

    private Predicate buildIsNullPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isNull(root.get(condition.field));
    }

    public static Filter convertFilter(Filter filter){
        Filter newFilter = new Filter();
        for(Condition condition: filter.getConditions()){
            if(condition.getField()!=null && condition.getField().equalsIgnoreCase("active")){
                if(condition.getValue()==null){
                    condition.setValue(1);
                }
            }
            if(condition.getValue()!=null && String.valueOf(condition.getValue()).trim().length()>0){
                newFilter.addCondition(new Condition.Builder().setComparison(condition.getComparison()).setField(condition.getField()).setValue(condition.getValue()).setType(condition.getType()).build());
            }
        }
        if(newFilter.getConditions().size()==0){
            newFilter.addCondition(new Condition.Builder().setComparison(Comparison.eq).setField("active").setValue(1).setType(Type.numeric).build());
        }
        return newFilter;
    }
}