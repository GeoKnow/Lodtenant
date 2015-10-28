package org.aksw.lodtenant.cli;

import java.util.List;

public interface RdfEntityManager
//    implements Entity
{
    <T> List<T> find(Class<T> clazz, Object primaryKey);
    void merge(Object object);
}
