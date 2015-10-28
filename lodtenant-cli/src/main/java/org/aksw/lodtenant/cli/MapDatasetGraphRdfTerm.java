package org.aksw.lodtenant.cli;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.sparql.core.DatasetGraph;

public class MapDatasetGraphRdfTerm
    extends AbstractMap<QuadCoordinate, String>
{
    protected DatasetGraph datasetGraph;

    public MapDatasetGraphRdfTerm(DatasetGraph datasetGraph) {
        this.datasetGraph = datasetGraph;
    }

    @Override
    public String get(Object coordinate) {
        String result = null;
        if(coordinate instanceof QuadCoordinate) {
            QuadCoordinate coord = (QuadCoordinate)coordinate;
            result = TalisRdfTerm.getComponent(datasetGraph, coord);
        }
        return result;
    };

    @Override
    public Set<Entry<QuadCoordinate, String>> entrySet() {
        Map<QuadCoordinate, String> map = TalisRdfTerm.createCoordinateMap(datasetGraph);
        Set<Entry<QuadCoordinate, String>> result = map.entrySet();
        return result;
    }
}
