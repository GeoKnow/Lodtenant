package org.aksw.lodtenant.manager.domain;

import org.aksw.jena_sparql_api.mapper.annotation.DefaultIri;
import org.aksw.jena_sparql_api.mapper.annotation.Iri;
import org.aksw.jena_sparql_api.mapper.annotation.RdfType;

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
