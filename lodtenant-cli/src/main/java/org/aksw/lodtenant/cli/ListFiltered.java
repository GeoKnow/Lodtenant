package org.aksw.lodtenant.cli;

import java.util.AbstractList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ListFiltered<T>
    extends AbstractList<T>
{
    protected List<T> base;
    protected Predicate<T> predicate;

    protected BiMap<Integer, Integer> createIndexMap() {
        BiMap<Integer, Integer> result = HashBiMap.create();
        int j = 0;
        for(int i = 0; i < base.size(); ++i) {
            T item = base.get(i);

            boolean isAccepted = predicate.apply(item);
            if(isAccepted) {
                result.put(j, i);
                ++j;
            }
        }
        return result;
    }


    @Override
    public T get(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }
}
