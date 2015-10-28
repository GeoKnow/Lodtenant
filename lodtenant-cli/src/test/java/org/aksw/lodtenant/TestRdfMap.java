package org.aksw.lodtenant;

import java.io.IOException;
import java.util.Map;

import org.aksw.jena_sparql_api.utils.ModelDiff;
import org.aksw.lodtenant.cli.MapDatasetGraphRdfTerm;
import org.aksw.lodtenant.cli.QuadCoordinate;
import org.aksw.lodtenant.cli.TalisRdfTerm;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetGraphFactory;
import com.vividsolutions.jts.util.Assert;

public class TestRdfMap {
    @Test
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

        ModelDiff diff = ModelDiff.create(m, roundtripModel);
        Assert.isTrue(diff.isEmpty());

    }

}
