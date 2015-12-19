package org.aksw.lodtenant.cli;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

public class EntityManagerUtils {

    public static <T> T findByAttribute(EntityManager em, Class<T> clazz, String attrName, Object value) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<T> r = q.from(clazz);
        ParameterExpression<String> aliasPlaceholder = cb.parameter(String.class);
        q.where(cb.equal(r.get(attrName), aliasPlaceholder));

        T result = em.createQuery(q).getSingleResult();

        return result;
    }


}
