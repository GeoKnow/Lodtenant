package org.aksw.rdfmap.model;

import org.aksw.jena_sparql_api.concepts.Relation;

public class RdfProperty {
    /**
     * The name of the attribute
     */
    protected String name;

    /**
     * The corresponding RDF predicate
     */
    protected Relation relation;



    public RdfProperty(String name, Relation relation) {
        super();
        this.name = name;
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public Relation getRelation() {
        return relation;
    }
}
