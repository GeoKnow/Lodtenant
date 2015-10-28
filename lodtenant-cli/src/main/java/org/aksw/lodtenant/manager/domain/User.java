package org.aksw.lodtenant.manager.domain;

import org.aksw.rdfmap.annotation.DefaultIri;
import org.aksw.rdfmap.annotation.Iri;

@RdfType("foaf:Agent")
@DefaultIri("user:#{name}")
public class User {

    @Iri("foaf:name")
    protected String name;

    public User(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
