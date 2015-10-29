package org.aksw.lodtenant.cli;

import com.google.common.base.Function;
import com.hp.hpl.jena.graph.Node;

class F_NodeToRdfTerm
    implements Function<Node, TalisRdfTerm>
{
    @Override
    public TalisRdfTerm apply(Node node) {
        TalisRdfTerm result = TalisRdfTerm.create(node);
        return result;
    }

    public static final F_NodeToRdfTerm fn = new F_NodeToRdfTerm();
}

class F_RdfTermToNode
    implements Function<TalisRdfTerm, Node>
{

    @Override
    public Node apply(TalisRdfTerm term) {
        Node result = TalisRdfTerm.createNode(term);
        return result;
    }

    public static final F_RdfTermToNode fn = new F_RdfTermToNode();
}


//public class ListAddTransform<O>
//    extends
//{
//    protected Function<I, O> iToO;
//    protected Function<O>
//
//}
