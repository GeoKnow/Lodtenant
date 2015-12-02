package org.aksw.lodtenant.repo.rdf;

import org.aksw.jena_sparql_api.mapper.annotation.DefaultIri;
import org.aksw.jena_sparql_api.mapper.annotation.Iri;
import org.aksw.jena_sparql_api.mapper.annotation.RdfType;

import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

//@Namespace("o: <http://foobar.org>")
//@RdfGen"")
// By default, classes are 'TypeIdentifiable', this means, that instances of a class
// can be collected by instances of the given RdfType
// Note: this annotation creates a default id, it is not mandatory for existing instances to follow this pattern

//@DefaultIri("o:job-#{#md5(content)}-#{#localName(owner)}")
//@DefaultIri("o:job-#{#md5(content)}-#{#encodeForUri(name)}")
@DefaultIri("o:job-#{#md5(content)}-#{name}")
@RdfType("lodflow:Job") // TODO Possibly add flag to control removal behavior: remove-on-delete, remove-on-empty
public class JobSpec {
    //protected Set<String> aliases;
    @Iri("o:content")
    protected String content;

    protected String owner;

    @Iri("rdfs:label")
    protected String name;
    //protected List<String> aliases;

    public JobSpec() {

    }

    // @Iri("propertyName")
    // @Embedded("sub-uri")
    // @MappedBy("attributeOfForeignClass")
    // List<User> contributors;
    public JobSpec(String content, String owner, String name) {
        super();
        this.content = content;
        this.owner = owner;
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
