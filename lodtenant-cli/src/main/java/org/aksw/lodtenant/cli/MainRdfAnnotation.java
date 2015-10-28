package org.aksw.lodtenant.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aksw.jena_sparql_api.core.SparqlService;
import org.aksw.jena_sparql_api.update.FluentSparqlService;
import org.aksw.lodtenant.manager.domain.User;
import org.aksw.lodtenant.manager.domain.Workflow;
import org.aksw.rdfmap.model.RdfClass;
import org.aksw.rdfmap.model.RdfClassFactory;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetGraphFactory;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.binding.BindingHashMap;
import com.hp.hpl.jena.vocabulary.XSD;

interface ValueHolder<T>
{
    void setValue(T o);
    T getValue();
}



class SupplierRdfObjects
    implements Supplier<List<Node>>
{

    @Override
    public List<Node> get() {
        // TODO Auto-generated method stub
        return null;
    }

}

class CoordinateGraph {
    protected Map<QuadCoordinate, String> coordToValue;

    public CoordinateGraph() {
        this.coordToValue = new HashMap<QuadCoordinate, String>();
    }

    public CoordinateGraph(Map<QuadCoordinate, String> coordToValue) {
        super();
        this.coordToValue = coordToValue;
    }
}

//
//class ValueHolderRdf
//    implements ValueHolder<Object>
//{
//    protected TypeMapper typeMapper;
//    protected Class<?> valueClazz;
//
//    protected Supplier<List<Node>> rdfObjectSupplier;
//    protected QuadCoordinate coordinate;
//
//
//
//
//    //protected Node defaultNode; // The default node from which
//    //protected Supplier<Node> defaultNodeSupplier;
//    //protected Expr javaToRdf;
//    //protected Expr sparqlTransform;
//    //Convert a java object to a lexical representation for the given coordinate
//    //protected Function<Object, String> javaToRdf;
//
//    // Convert an RDF node to a java object
//    protected Function<Node, Object> rdfToJava;
//
//    public ValueHolderRdf()
//    {
//        this(TypeMapper.getInstance());
//    }
//
//    public ValueHolderRdf(TypeMapper typeMapper) {
//        this.typeMapper = typeMapper;
//    }
//
//    /**
//     * Sets a value from a java object.
//     *
//     * If the value is of type java.lang.String, the lexical value of the json talis rdf is updated.
//     * Non string values imply typed-literals
//     *
//     */
//    @Override
//    public void setValue(Object o) {
//        if(o != null) {
//            Class<?> clazz = o.getClass();
//            RDFDatatype datatype = typeMapper.getTypeByClass(clazz);
//            if(datatype == null) {
//                throw new RuntimeException("No type mapper for " + clazz + ": " + o);
//            }
//
//            String lex = datatype.unparse(o);
//            Node node = NodeFactory.createLiteral(lex, datatype);
//
//
////            BindingHashMap binding = new BindingHashMap(baseBinding);
////            binding.add(Var.alloc("value"), node);
////            Node javaToRdf.eval(binding, null);
//        }
//
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public Object getValue() {
//        Node node = TalisRdfTermUtilsOld.getNodeAt(datasetGraph, coordinate);
//
//        Object o = node.getLiteralValue();
//
//        //datatype.parse(lexicalForm)
//
////        Node node = getNode(coordinate);
////        if(node == null) {
////            RDFDatatype datatype = typeMapper.get
////
////        }
//
//
//
//
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//}


public class MainRdfAnnotation {
    public static void main(String[] args) throws NoSuchMethodException, SecurityException {

        RdfClassFactory f = RdfClassFactory.createDefault();
        RdfClass rc = f.create(Workflow.class);

        Workflow x = (Workflow)rc.createProxy(DatasetGraphFactory.createMem(), NodeFactory.createURI("http://workflow1"));

        System.out.println("WOOOHOOO" + x);

        SparqlService ss = FluentSparqlService.http("http://localhost:8890/sparql", "http://rdfmap.org/").create();

        RdfEntityManager entityManager = new RdfEntityManagerImpl(null, null, ss);
        entityManager.find(Workflow.class, "http://foobar");

        Workflow w = new Workflow("{}", new User("franz"));

        System.out.println("SUBJECT IS " + rc.getSubject(w));
    }
}