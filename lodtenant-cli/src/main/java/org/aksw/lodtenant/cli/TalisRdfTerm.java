package org.aksw.lodtenant.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aksw.jena_sparql_api.utils.ListObjectsOfDatasetGraph;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.sparql.core.DatasetGraphFactory;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.vocabulary.XSD;

/**
 *
 * There are no NULL values in this object, only empty strings
 *
 * @author raven
 *
 */
public class TalisRdfTerm {
    protected String type;
    protected String value;
    protected String datatype;
    protected String lang;

    public static TalisRdfTerm update(TalisRdfTerm term, int component, String value) {
        List<String> cs = TalisRdfTerm.toArray(term);
        cs.set(component, value);
        TalisRdfTerm result = TalisRdfTerm.create(cs);
        return result;
    }

    public TalisRdfTerm(String type, String value,
            String datatype, String lang) {
        super();
        this.type = type;
        this.value = value;
        this.datatype = datatype;
        this.lang = lang;
    }

    public static List<String> toArray(TalisRdfTerm term) {
        List<String> tmp = Arrays.asList(term.getType(), term.getValue(), term.getDatatype(), term.getLang());
        List<String> result = new ArrayList<String>(tmp);
        return result;
    }

    public static TalisRdfTerm create(List<String> arr) {
        if(arr.size() != 4) {
            throw new RuntimeException("Exactly 4 components needed");
        }

        String type = arr.get(0);
        String value = arr.get(1);
        String datatype = arr.get(2);
        String lang = arr.get(3);

        TalisRdfTerm result = new TalisRdfTerm(type, value, datatype, lang);
        return result;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getDatatype() {
        return datatype;
    }

    public String getLang() {
        return lang;
    }

    public String getComponent(int c) {
        String result;
        switch(c) {
        case 0:
            result = type;
            break;
        case 1:
            result = value;
            break;
        case 2:
            result = datatype;
            break;
        case 3:
            result = lang;
            break;
        default:
            throw new RuntimeException("should not happen");
        }
        return result;
    }


    public static TalisRdfTerm create(Node node) {
        TalisRdfTerm result;
        if(node.isURI()) {
            result = createUri(node.getURI());
        } else if(node.isLiteral()) {
            result = createLiteral(node.getLiteralLexicalForm(), node.getLiteralDatatypeURI(), node.getLiteralLanguage());
        } else if(node.isBlank()) {
            result = createBlankNode(node.getBlankNodeId().getLabelString());
        } else {
            throw new RuntimeException("Unkown node type");
        }

        return result;
    }

    public static TalisRdfTerm createBlankNode(String value) {
        TalisRdfTerm result = new TalisRdfTerm("bnode", value, "", "");
        return result;
    }

    public static TalisRdfTerm createUri(String uri) {
        TalisRdfTerm result = new TalisRdfTerm("uri", uri, "", "");
        return result;
    }

    public static TalisRdfTerm ceratePlainLiteral(String value) {
        TalisRdfTerm result = new TalisRdfTerm("literal", value, XSD.xstring.getURI(), "");
        return result;
    }

    public static TalisRdfTerm createPlainLiteral(String value, String lang) {
        TalisRdfTerm result = new TalisRdfTerm("literal", value, XSD.xstring.getURI(), lang);
        return result;
    }

    public static TalisRdfTerm createTypedLiteral(String value, String datatype) {
        TalisRdfTerm result = new TalisRdfTerm("literal", value, datatype, "");
        return result;
    }

    public static TalisRdfTerm createLiteral(String value, String datatype, String lang) {
        TalisRdfTerm result = new TalisRdfTerm("literal", value, datatype, lang);
        return result;
    }

    public static String getTermType(Node node) {
        String result;
        if(node.isURI()) {
            result = "uri";
        } else if(node.isLiteral()) {
            result = "literal";
        } else if(node.isBlank()) {
            result = "bnode";
        } else {
            throw new RuntimeException("Should not happen");
        }

        return result;
    }

    public static String getValue(Node node) {
        String result;
        if(node.isURI()) {
            result = node.getURI();
        }
        else if(node.isLiteral()) {
            result = node.getLiteralLexicalForm();
        } else {
            throw new RuntimeException("should not happen");
        }

        return result;
    }

    public static String getLang(Node node) {
        String result = node.isLiteral() ? node.getLiteralLanguage() : "";
        return result;
    }

    public static String getDatatype(Node node) {
        String result = node.isLiteral() ? node.getLiteralDatatypeURI() : "";
        return result;
    }

    public static Node createNode(TalisRdfTerm term) {
        Node result;
        String type = term.getType();
        String value = term.getValue();
        String datatype = term.getDatatype();
        String lang = term.getLang();

        if("uri".equals(type)) {
            result = NodeFactory.createURI(value);
        } else if("literal".equals(type)) {
            TypeMapper typeMapper = TypeMapper.getInstance();
            RDFDatatype dt = typeMapper.getSafeTypeByName(datatype);
            result = NodeFactory.createLiteral(value, lang, dt);
        } else if("bnode".equals(type)) {
            result = NodeFactory.createBlankNode(value);//.createAnon(new AnonId(value));
        } else {
            throw new RuntimeException("Should not happen");
        }
        return result;
    }

    public static String getComponent(Node node, int c) {
        String result;
        switch(c) {
        case 0:
            result = getTermType(node);
            break;
        case 1:
            result = getValue(node);
            break;
        case 2:
            result = getDatatype(node);
            break;
        case 3:
            result = getLang(node);
            break;
        default:
            throw new RuntimeException("should not happen");
        }
        return result;
    }

    public static List<Node> getObjects(DatasetGraph datasetGraph, Node g, Node s, Node p) {
        List<Node> result = ListObjectsOfDatasetGraph.create(datasetGraph, g, s, p);

        return result;
    }

    public static String getComponent(DatasetGraph datasetGraph, QuadCoordinate coord) {
        Node g = coord.getG();
        Node s = coord.getS();
        Node p = coord.getP();

        List<Node> os = getObjects(datasetGraph, g, s, p);

        int i = coord.getI();
        Node o = os.get(i);

        int c = coord.getC();

        String result = getComponent(o, c);
        return result;
    }

    public static Map<QuadCoordinate, String> createCoordinateMap(DatasetGraph datasetGraph) {
        Map<Triple, Integer> gspToCount = new HashMap<Triple, Integer>();

        Map<QuadCoordinate, String> result = new HashMap<QuadCoordinate, String>();
        Iterator<Quad> it = datasetGraph.find();
        while(it.hasNext()) {
            Quad quad = it.next();

            Node g = quad.getGraph();
            Node s = quad.getSubject();
            Node p = quad.getPredicate();
            Node o = quad.getObject();

            Triple gsp = new Triple(g, s, p);

            gspToCount.compute(gsp, (k, v) -> v == null ? 1 : v + 1);
            //MapUtils.increment(gspToCount, gsp);
            Integer i = gspToCount.get(gsp) - 1;
            for(int c = 0; c < 4; ++c) {
                String v = TalisRdfTerm.getComponent(o, c);

                QuadCoordinate coord = new QuadCoordinate(g, s, p, i, c);
                result.put(coord, v);
            }
        }


        return result;
    }

    public static String getComponent(Map<QuadCoordinate, String> map, Triple gsp, int i, int c) {
        Node g = gsp.getSubject();
        Node s = gsp.getPredicate();
        Node p = gsp.getObject();

        QuadCoordinate key = new QuadCoordinate(g, s, p, i, c);
        String result = map.get(key);
        return result;
    }

    public static Map<QuadCoordinate, String> createMap(DatasetGraph datasetGraph) {
        //DatasetGraphSimpleMem x;

        Map<QuadCoordinate, String> result = new HashMap<QuadCoordinate, String>(new MapDatasetGraphRdfTerm(datasetGraph));
        return result;
    }

    public static Map<Node, Map<Node, Map<Node, List<Node>>>> createTalisGraph(DatasetGraph dg) {
        return null;
    }

    public static DatasetGraph assembleDatasetGraph(Map<QuadCoordinate, String> map, TalisRdfTerm defaults) {
        DatasetGraph result = DatasetGraphFactory.create();//createMem();

        for(QuadCoordinate qc : map.keySet()) {
            Node g = qc.getG();
            Node s = qc.getS();
            Node p = qc.getP();

            Triple gsp = new Triple(g, s, p);

            int i = qc.getI();

            String type = getComponent(map, gsp, i, 0);
            String value = getComponent(map, gsp, i, 1);
            String datatype = getComponent(map, gsp, i, 2);
            String lang = getComponent(map, gsp, i, 3);

//            if(!StringUtils.isEmpty(lang)) {
//                System.out.println(lang);
//            }

            TalisRdfTerm term = new TalisRdfTerm(type, value, datatype, lang);

            Node o = TalisRdfTerm.createNode(term);

            Quad quad = new Quad(g, s, p, o);
            result.add(quad);
        }

        return result;
    }
}

