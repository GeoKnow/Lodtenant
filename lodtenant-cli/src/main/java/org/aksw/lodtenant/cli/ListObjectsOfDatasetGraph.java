package org.aksw.lodtenant.cli;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.Quad;

public class ListObjectsOfDatasetGraph
    extends AbstractList<Node>
{
    protected DatasetGraph datasetGraph;
    protected Quad quad;

    public ListObjectsOfDatasetGraph(DatasetGraph datasetGraph, Quad quad) {
        this.datasetGraph = datasetGraph;
        this.quad = quad;
    }

    public Quad createQuad(Node o) {
        Node g = quad.getGraph();
        Node s = quad.getSubject();
        Node p = quad.getPredicate();

        Quad result = new Quad(g, s, p, o);

        return result;
    }

    @Override
    public boolean add(Node o) {
        Quad quad = createQuad(o);

        boolean isContained = datasetGraph.contains(quad);
        if(!isContained) {
            datasetGraph.add(quad);
        }

        return true;
    }

    @Override
    public boolean remove(Object obj) {
        boolean result = false;
        if(obj instanceof Node) {
            Node o = (Node)obj;
            Quad quad = createQuad(o);

            result = datasetGraph.contains(quad);
            datasetGraph.delete(quad);
        }

        return result;
    }

    public static List<Node> getObjects(DatasetGraph datasetGraph, Quad quad) {
        Iterator<Quad> it = datasetGraph.find(quad);
        List<Quad> quads = Lists.newArrayList(it);
        List<Node> result = Lists.transform(quads, F_QuadGetObject.fn);
        return result;
    }

    @Override
    public Node get(int index) {
        List<Node> objects = getObjects(datasetGraph, quad);
        Node result = objects.get(index);
        return result;
    }

    @Override
    public int size() {
        Iterator<Quad> it = datasetGraph.find(quad);
        int result = Iterators.size(it);
        return result;
    }

    public static ListObjectsOfDatasetGraph create(DatasetGraph datasetGraph, Node g, Node s, Node p) {
        Quad quad = new Quad(g, s, p, null);
        ListObjectsOfDatasetGraph result = new ListObjectsOfDatasetGraph(datasetGraph, quad);
        return result;
    }
}

