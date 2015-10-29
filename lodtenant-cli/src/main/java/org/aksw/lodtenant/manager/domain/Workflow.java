package org.aksw.lodtenant.manager.domain;

import org.aksw.rdfmap.annotation.DefaultIri;
import org.aksw.rdfmap.annotation.Iri;

//@Namespace("o: <http://foobar.org>")
//@RdfGen"")
// By default, classes are 'TypeIdentifiable', this means, that instances of a class
// can be collected by instances of the given RdfType
// Note: this annotation creates a default id, it is not mandatory for existing instances to follow this pattern
@DefaultIri("o:#{#md5(content)}-#{owner.name}")
@RdfType("lodflow:Workflow") // TODO Possibly add flag to control removal behavior: remove-on-delete, remove-on-empty
public class Workflow {
    //protected Set<String> aliases;
    @Iri("o:foobar")
    protected String content;

    protected User owner;

    public Workflow() {

    }

    // @Iri("propertyName")
    // @Embedded("sub-uri")
    // @MappedBy("attributeOfForeignClass")
    // List<User> contributors;
    public Workflow(String content, User owner) {
        super();
        this.content = content;
        this.owner = owner;
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
}
