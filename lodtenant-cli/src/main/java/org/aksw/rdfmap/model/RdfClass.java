package org.aksw.rdfmap.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.aksw.jena_sparql_api.batch.ResourceShapeBuilder;
import org.aksw.jena_sparql_api.concepts.Relation;
import org.aksw.jena_sparql_api.concepts.RelationUtils;
import org.aksw.jena_sparql_api.mapper.MappedConcept;
import org.aksw.jena_sparql_api.shape.ResourceShape;
import org.aksw.rdfmap.proxy.MethodInterceptorRdf;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.Factory;

import com.google.common.base.Function;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetGraphFactory;
import com.hp.hpl.jena.sparql.core.Prologue;

public class RdfClass {
    /**
     * The affected class (maybe we should use the fully qualified class name instead?)
     */
    protected Class<?> targetClass;

    /**
     * A SpEL expression for the default IRI
     */
    //protected String defaultIriExpr;
    protected Function<Object, String> defaultIriFn;

    protected Prologue prologue = new Prologue();



    public RdfClass(Class<?> targetClass, Function<Object, String> defaultIriFn,
            Map<String, RdfProperty> propertyToMapping) {
        super();
        this.targetClass = targetClass;
        this.defaultIriFn = defaultIriFn;
        this.propertyToMapping = propertyToMapping;
    }


    public MappedConcept<DatasetGraph> getMappedQuery() {
        ResourceShapeBuilder builder = new ResourceShapeBuilder(prologue);


        for(RdfProperty p : propertyToMapping.values()) {
            builder.outgoing(p.getRelation());
        }

        ResourceShape shape = builder.getResourceShape();
        MappedConcept<DatasetGraph> result = ResourceShape.createMappedConcept2(shape, null);
        return result;
    }

    public static MethodInterceptorRdf getMethodInterceptor(Object o) {
        MethodInterceptorRdf result = null;

        if(o != null && Enhancer.isEnhanced(o.getClass())) {
            Factory factory = (Factory)o;
            Callback callback = factory.getCallback(0);
            result = callback != null && callback instanceof MethodInterceptorRdf
                    ? (MethodInterceptorRdf)callback : null;
        }

        return result;
    }

    /**
     * Returns the subject of a given object or null if not present.
     * First the object is checked for whether it is a proxy referring to a prior subject, which is returned if present.
     * Otherwise, a default iri will be generated.
     *
     * @param o
     * @return
     */
    public Node getSubject(Object o) {
        MethodInterceptorRdf m = getMethodInterceptor(o);
        Node result = m != null ? m.getPresetSubject() : null;

        if(result == null) {
            String str = defaultIriFn != null ? defaultIriFn.apply(o) : null;
            result = str != null ? NodeFactory.createURI(str) : null;
        }

        return result;
    }


    public DatasetGraph createDatasetGraph(Object o) {
        DatasetGraph result = DatasetGraphFactory.createMem();
        Collection<RdfProperty> rdfProperties = propertyToMapping.values();
        Node s = getSubject(o);

        for(RdfProperty pd : rdfProperties) {
            Relation relation = pd.getRelation();
            Triple t = RelationUtils.extractTriple(relation);
            if(t != null) {
                Node p = t.getPredicate();
                int i = 0;

                //Node o = rep;
            }
        }

        return result;
    }


    // preconfigured triples
    //protected List<>

    // Usually a LinkedHashMap
    protected Map<String, RdfProperty> propertyToMapping = new LinkedHashMap<String, RdfProperty>();


    /**
     * Create a proxied instance of the class based on the given graph
     *
     * @param datasetGraph
     * @return
     */
    public Object createProxy(DatasetGraph datasetGraph, Node subject) {




        Object o;
        try {
            o = targetClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        MethodInterceptorRdf interceptor = new MethodInterceptorRdf(o, subject, datasetGraph);
        //new Class<?>[] { ProxiedRdf.class }
//        Object result = Enhancer.create(targetClass, null, interceptor);
        Object result = Enhancer.create(targetClass, null, interceptor);

        Factory x = (Factory)result;
        Callback cx = x.getCallback(0);
        MethodInterceptorRdf back = (MethodInterceptorRdf)cx;

        System.out.println("CALLBACKS " + back.getDatasetGraph());
        //Enhancer.isEnhanced(arg0)

        //Object ih = org.springframework.cglib.proxy.Proxy.p
        //Enhancer.
        //Proxy.getInvocationHandler(proxy)
        //org.springframework.cglib.proxy.Enhancer.
        //System.out.println("HANDLER: " + ih);

        return result;
    }

}
