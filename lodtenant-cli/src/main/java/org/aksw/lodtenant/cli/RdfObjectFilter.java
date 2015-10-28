package org.aksw.lodtenant.cli;

import java.util.List;

import com.google.common.base.Predicate;
import com.hp.hpl.jena.graph.Node;

public class RdfObjectFilter
    implements Predicate<Node>
{
    protected List<String> types;
    protected List<String> datatypes;
    protected List<String> langs;

    public RdfObjectFilter(List<String> types, List<String> datatypes,
            List<String> langs) {
        super();
        this.types = types;
        this.datatypes = datatypes;
        this.langs = langs;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<String> getDatatypes() {
        return datatypes;
    }

    public List<String> getLangs() {
        return langs;
    }

    @Override
    public boolean apply(Node node) {
        String type = node.isURI() ? "uri" : (node.isLiteral() ? "literal" : (node.isBlank() ? "bnode" : (node.isVariable() ? "variable" : "unkown")));
        String lang = node.isLiteral() ? node.getLiteralLanguage() : null;
        String dtype = node.isLiteral() ? node.getLiteralDatatypeURI() : null;

        boolean typeMatch = (types == null ? true : types.contains(type));
        boolean langMatch = (langs == null ? true : langs.contains(lang));
        boolean dtypeMatch = (datatypes == null ? true : datatypes.contains(dtype));

        // TODO Handle lang matches properly according to SPARQL's langMatches function

        boolean result = typeMatch && langMatch && dtypeMatch;
        return result;
    }
}
