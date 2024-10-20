package banquemisr.challenge05.task_management.entity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
public class TaskSpecification implements Specification<Task> {
    private final Task criteria;
    public TaskSpecification(Task criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getTitle() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + criteria.getTitle() + "%"));
        }
        if (criteria.getDescription() != null) {
            predicates.add(criteriaBuilder.like(root.get("description"), "%" + criteria.getDescription() + "%"));
        }
        if (criteria.getStatus() != null) {

            predicates.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
        }
        if (criteria.getDueDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("dueDate"), criteria.getDueDate()));
        }
        if (criteria.getUser() != null) {
            predicates.add(criteriaBuilder.equal(root.get("user"), criteria.getUser()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
