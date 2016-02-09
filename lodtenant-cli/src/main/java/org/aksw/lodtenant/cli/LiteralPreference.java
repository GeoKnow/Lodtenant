package org.aksw.lodtenant.cli;

import java.util.List;

import org.apache.jena.graph.Node;

public class LiteralPreference {
    protected List<String> langs;
    protected List<Node> properties;
    protected boolean preferProperties = false;

    public LiteralPreference(List<String> langs, List<Node> properties,
            boolean preferProperties) {
        super();
        this.langs = langs;
        this.properties = properties;
        this.preferProperties = preferProperties;
    }

    public List<String> getLangs() {
        return langs;
    }

    public List<Node> getProperties() {
        return properties;
    }

    public boolean isPreferProperties() {
        return preferProperties;
    }
}
