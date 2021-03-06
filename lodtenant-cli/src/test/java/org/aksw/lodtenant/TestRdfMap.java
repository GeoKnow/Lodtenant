package org.aksw.lodtenant;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.aksw.jena_sparql_api.utils.ListObjectsOfDatasetGraph;
import org.aksw.jena_sparql_api.utils.ModelDiff;
import org.aksw.lodtenant.cli.MapDatasetGraphRdfTerm;
import org.aksw.lodtenant.cli.QuadCoordinate;
import org.aksw.lodtenant.cli.TalisRdfTerm;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetGraphFactory;
import com.hp.hpl.jena.sparql.core.Quad;
import com.hp.hpl.jena.vocabulary.RDFS;

public class TestRdfMap {
    //@Test
    public void testMap() throws IOException {
        Resource r = new ClassPathResource("dbpedia-airport-excerpt.nt");

        Model m = ModelFactory.createDefaultModel();
        m.read(r.getInputStream(), "http://example.org/base/", "N-TRIPLES");
        Graph graph = m.getGraph();
        DatasetGraph dg = DatasetGraphFactory.create(graph);

        Map<QuadCoordinate, String> map = new MapDatasetGraphRdfTerm(dg);
        DatasetGraph roundtripDg = TalisRdfTerm.assembleDatasetGraph(map, new TalisRdfTerm("", "", "", ""));

        Graph roundtripGraph = roundtripDg.getDefaultGraph();
        Model roundtripModel = ModelFactory.createModelForGraph(roundtripGraph);

        //m.write(System.out, "TURTLE");

        ModelDiff diff = ModelDiff.create(m, roundtripModel);
        Assert.assertTrue(diff.isEmpty());
    }

    //@Test
    public void test2() throws IOException {
        Resource r = new ClassPathResource("dbpedia-airport-excerpt.nt");
        Model m = ModelFactory.createDefaultModel();
        m.read(r.getInputStream(), "http://example.org/base/", "N-TRIPLES");

        DatasetGraph dg = DatasetGraphFactory.create(m.getGraph());
        Node g = Quad.defaultGraphIRI;
        //Node g = Node.ANY;
        Node s = NodeFactory.createURI("http://dbpedia.org/resource/London_Stansted_Airport");
        Node p = RDFS.label.asNode();
        List<Node> nodes = ListObjectsOfDatasetGraph.create(dg, g, s, p);
        //nodes.remove(0);
        nodes.set(0, NodeFactory.createURI("test"));
        System.out.println(nodes);
    }
}
