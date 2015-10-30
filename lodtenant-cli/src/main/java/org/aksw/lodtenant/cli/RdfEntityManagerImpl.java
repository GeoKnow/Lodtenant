package org.aksw.lodtenant.cli;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aksw.commons.collections.diff.Diff;
import org.aksw.jena_sparql_api.core.QueryExecutionFactory;
import org.aksw.jena_sparql_api.core.SparqlService;
import org.aksw.jena_sparql_api.core.UpdateExecutionFactory;
import org.aksw.jena_sparql_api.core.utils.UpdateDiffUtils;
import org.aksw.jena_sparql_api.core.utils.UpdateExecutionUtils;
import org.aksw.jena_sparql_api.lookup.LookupService;
import org.aksw.jena_sparql_api.lookup.LookupServiceUtils;
import org.aksw.jena_sparql_api.mapper.MappedConcept;
import org.aksw.jena_sparql_api.utils.DatasetDescriptionUtils;
import org.aksw.jena_sparql_api.utils.DatasetGraphUtils;
import org.aksw.rdfmap.model.RdfClass;
import org.aksw.rdfmap.model.RdfClassFactory;
import org.aksw.rdfmap.proxy.MethodInterceptorRdf;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.sparql.core.DatasetDescription;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetGraphFactory;
import com.hp.hpl.jena.sparql.core.Prologue;
import com.hp.hpl.jena.sparql.core.Quad;

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
    public <T> T find(Class<T> clazz, Object primaryKey) {

        RdfClassFactory rdfClassFactory = RdfClassFactory.createDefault(prologue);
        RdfClass rdfClass = rdfClassFactory.create(clazz);

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
        //System.out.println(dg);


        Object proxy = rdfClass.createProxy(dg, node);

        rdfClass.setValues(proxy, dg);


        T result = (T)proxy;
        //List<T> result = Collections.<T>singletonList(item);
        return result;
    }

    @Override
    public void merge(Object object) {
        MethodInterceptorRdf interceptor = RdfClass.getMethodInterceptor(object);

        DatasetGraph oldState = interceptor == null
                ? DatasetGraphFactory.createMem()
                : interceptor.getDatasetGraph()
                ;

        Class<?> clazz = object.getClass();
        RdfClass rdfClass = RdfClassFactory.createDefault(prologue).create(clazz);


        DatasetDescription datasetDescription = sparqlService.getDatasetDescription();
        String gStr = DatasetDescriptionUtils.getSingleDefaultGraphUri(datasetDescription);
        if(gStr == null) {
            throw new RuntimeException("No target graph specified");
        }
        Node g = NodeFactory.createURI(gStr);

        DatasetGraph newState = rdfClass.createDatasetGraph(object, g);

        System.out.println("oldState");
        DatasetGraphUtils.write(System.out, oldState);

        System.out.println("newState");
        DatasetGraphUtils.write(System.out, newState);

        Diff<Set<Quad>> diff = UpdateDiffUtils.computeDelta(newState, oldState);
        System.out.println("diff: " + diff);
        UpdateExecutionFactory uef = sparqlService.getUpdateExecutionFactory();
        UpdateExecutionUtils.executeUpdate(uef, diff);


//        UpdateExecutionUtils.executeUpdateDelta(uef, newState, oldState);
    }
}
