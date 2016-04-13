package org.aksw.lodtenant.cli;

import java.util.AbstractList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import org.apache.jena.graph.Node;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.sparql.core.Quad;


class OverridingList
    extends AbstractList<Object>
{
    protected Quad quad;
    protected List<Node> baseList;
    protected Map<QuadCoordinate, String> override;
    protected Function<Object, Node> objectToNodeFn;



    @Override
    public Object set(int index, Object result) {
        //
        if(result instanceof String) {
            Node node = objectToNodeFn.apply(result);
        }


        return result;
    }

    @Override
    public Object get(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }
}

public class DatasetGraphOverride
{
    protected DatasetGraph baseDatasetGraph;
    protected Map<QuadCoordinate, String> override;
}
