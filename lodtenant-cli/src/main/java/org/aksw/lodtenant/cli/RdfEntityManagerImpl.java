package org.aksw.lodtenant.cli;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.aksw.jena_sparql_api.core.QueryExecutionFactory;
import org.aksw.jena_sparql_api.core.SparqlService;
import org.aksw.jena_sparql_api.lookup.LookupService;
import org.aksw.jena_sparql_api.lookup.LookupServiceUtils;
import org.aksw.jena_sparql_api.mapper.MappedConcept;
import org.aksw.rdfmap.model.RdfClass;
import org.aksw.rdfmap.model.RdfClassFactory;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetGraphFactory;
import com.hp.hpl.jena.sparql.core.Prologue;

public class RdfEntityManagerImpl
    implements RdfEntityManager
{
    protected Prologue prologue;

    /**
     * The languagePreferences acts as a default method to priorize and filter items in a set of (literal)
     * nodes, such as values of the rdfs:label or rdfs:comment properties.
     *
     */
    protected List<String> readLangs;

    /**
     * The default language which to apply to newly created RDF data
     */
    protected String writeLang;
    protected SparqlService sparqlService;



    public RdfEntityManagerImpl(Prologue prologue,
            List<String> readLangs, SparqlService sparqlService) {
        super();
        this.prologue = prologue;
        this.readLangs = readLangs;
        this.sparqlService = sparqlService;
    }

    @Override
    public <T> List<T> find(Class<T> clazz, Object primaryKey) {

        RdfClass rdfClass = RdfClassFactory.createDefault().create(clazz);

        MappedConcept<DatasetGraph> shape = rdfClass.getMappedQuery();

        QueryExecutionFactory qef = sparqlService.getQueryExecutionFactory();
        LookupService<Node, DatasetGraph> ls = LookupServiceUtils.createLookupService(qef, shape);

        Node node;
        if(primaryKey instanceof String) {
            node = NodeFactory.createURI((String)primaryKey);
        } else if(primaryKey instanceof Node) {
            node = (Node)primaryKey;
        } else {
            throw new RuntimeException("Invalid primary key type: " + primaryKey);
        }

        Map<Node, DatasetGraph> nodeToGraph = ls.apply(Collections.singleton(node));
        DatasetGraph dg = nodeToGraph.get(node);
        if(dg == null) {
            dg = DatasetGraphFactory.createMem();
        }
        System.out.println(dg);


        rdfClass.createProxy(dg, node);

System.out.println(shape);

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void merge(Object object) {
        // TODO Auto-generated method stub

    }
}
