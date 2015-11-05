package org.aksw.lodtenant.cli;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.aksw.rdfmap.jpa.EntityManagerJena;
import org.aksw.rdfmap.jpa.criteria.CriteriaBuilderJena;

public class EntityManagerUtils {

    public static <T> T findByAttribute(EntityManagerJena em, Class<T> clazz, String attrName, Object value) {
        CriteriaBuilderJena cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<T> r = q.from(clazz);
        ParameterExpression<String> aliasPlaceholder = cb.parameter(String.class);
        q.where(cb.equal(r.get(attrName), aliasPlaceholder));

        T result = em.createQuery(q).getSingleResult();

        return result;
    }


}
