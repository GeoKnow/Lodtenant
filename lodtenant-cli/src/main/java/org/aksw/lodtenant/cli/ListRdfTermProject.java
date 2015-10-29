package org.aksw.lodtenant.cli;

import java.util.AbstractList;
import java.util.List;

public class ListRdfTermProject
    extends AbstractList<String>
{
    protected List<TalisRdfTerm> base;
    protected int component;
    protected TalisRdfTerm defaultTerm;

    public ListRdfTermProject(List<TalisRdfTerm> base,
            int component, TalisRdfTerm defaultTerm) {
        super();
        this.base = base;
        this.component = component;
        this.defaultTerm = defaultTerm;
    }

    @Override
    public String get(int index) {
        TalisRdfTerm term = base.get(index);
        String result = term.getComponent(component);
        return result;
    }

    @Override
    public boolean add(String value) {
        TalisRdfTerm item = TalisRdfTerm.update(defaultTerm, component, value);
        boolean result = base.add(item);
        return result;
    }

    @Override
    public String set(int index, String value) {
        TalisRdfTerm old = base.get(index);
        TalisRdfTerm now = TalisRdfTerm.update(old, component, value);
        base.set(index, now);

        return value;
    }

    @Override
    public String remove(int index) {
        TalisRdfTerm term = base.remove(index);
        String result = term.getComponent(component);
        return result;
    }

    @Override
    public int size() {
        int result = base.size();
        return result;
    }


}
