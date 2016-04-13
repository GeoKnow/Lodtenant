package org.aksw.lodtenant.cli;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.sparql.core.DatasetGraphFactory;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.expr.Expr;

class TalisRdfTermUtilsOld {

    public DatasetGraph toGraph(Map<QuadCoordinate, String> coordMap, TalisRdfTerm defaultTerm) {
        if(defaultTerm == null) {
            defaultTerm = TalisRdfTerm.createTypedLiteral("", "");
        }

        DatasetGraph result = DatasetGraphFactory.createMem();

        // Iterate all coordinates and

        return result;
    }

    public Map<QuadCoordinate, String> createCoordinateMap(DatasetGraph datasetGraph) {
        Iterator<Quad> it = datasetGraph.find();
        while(it.hasNext()) {
            Quad quad = it.next();

            String g = quad.getGraph().getURI();
            String s = quad.getSubject().getURI(); // TODO handle blank nodes
            String p = quad.getPredicate().getURI();

            Node o = quad.getObject();
            TalisRdfTerm to = TalisRdfTerm.create(o);



        }
        return null;
    }

    public static Node getNodeAt(DatasetGraph datasetGraph, QuadCoordinate coordinate) {
        return null;
    }

    // Term type / lang tag
    public static void setValue(DatasetGraph datasetGraph, QuadCoordinate coordinate, Object value) {

    }


    public List<Node> listObjects(Graph graph, Expr expr) {
        //GraphUtils.findRootByType(model, atype)
        //Set<Set<Expr>> cnf = CnfUtils.toSetCnf(expr);
        //NfUtils.
        // TODO Extract single element clauses and of those extract equal constraints
        // i.e. a mapping from <Var, Node>

        // TODO Extract triple constraints from expr for performance
        Node s = null;
        Node p = null;
        Node o = null;
        List<Node> objects = graph.find(s, p, o).mapWith(t -> t.getObject()).toList();

        return null;


    }
}