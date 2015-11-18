package org.aksw.lodtenant.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aksw.jena_sparql_api.core.SparqlService;
import org.aksw.jena_sparql_api.mapper.jpa.EntityManagerJena;
import org.aksw.jena_sparql_api.update.FluentSparqlService;
import org.aksw.jena_sparql_api.utils.DatasetDescriptionUtils;
import org.aksw.lodtenant.manager.domain.WorkflowSpec;

import com.google.common.base.Supplier;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.core.Prologue;

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

        Prologue prologue = new Prologue();
        prologue.setPrefix("o", "http://example.org/");
        SparqlService ss = FluentSparqlService.http("http://localhost:8890/sparql", "http://rdfmap.org/").create();
        EntityManagerJena em = new EntityManagerJena(prologue, null, ss);


        System.out.println(DatasetDescriptionUtils.toString(ss.getDatasetDescription()));

        //Workflow wa = em.find(Workflow.class, "http://example.org/99914b932bd37a50b983c5e7c90ae93b-franz");

        WorkflowSpec wa = EntityManagerUtils.findByAttribute(em, WorkflowSpec.class, "alias", "my-workflow");




//        if(wa == null) {
//            wa = new Workflow("{}", new User("franz"), "franz");
//        }
        System.out.println("test: " + wa.getContent());

        //wa.setContent("yaaaaay");

        em.merge(wa);
    }
}
