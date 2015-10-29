package org.aksw.lodtenant.cli;

public interface RdfEntityManager
//    implements Entity
{
    <T> T find(Class<T> clazz, Object primaryKey);
    void merge(Object object);
}
