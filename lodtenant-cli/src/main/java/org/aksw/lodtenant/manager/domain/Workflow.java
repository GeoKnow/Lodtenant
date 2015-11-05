package org.aksw.lodtenant.manager.domain;

import org.aksw.jena_sparql_api.mapper.annotation.DefaultIri;
import org.aksw.jena_sparql_api.mapper.annotation.Iri;
import org.aksw.jena_sparql_api.mapper.annotation.RdfType;

//@Namespace("o: <http://foobar.org>")
//@RdfGen"")
// By default, classes are 'TypeIdentifiable', this means, that instances of a class
// can be collected by instances of the given RdfType
// Note: this annotation creates a default id, it is not mandatory for existing instances to follow this pattern
@DefaultIri("o:#{#md5(content)}-#{owner.name}")
@RdfType("lodflow:Workflow") // TODO Possibly add flag to control removal behavior: remove-on-delete, remove-on-empty
public class Workflow {
    //protected Set<String> aliases;
    @Iri("o:content")
    protected String content;

    protected User owner;

    @Iri("rdfs:label")
    protected String alias;
    //protected List<String> aliases;

    public Workflow() {

    }

    // @Iri("propertyName")
    // @Embedded("sub-uri")
    // @MappedBy("attributeOfForeignClass")
    // List<User> contributors;
    public Workflow(String content, User owner, String alias) {
        super();
        this.content = content;
        this.owner = owner;
        this.alias = alias;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
