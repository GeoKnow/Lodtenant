package org.aksw.lodtenant.cli;

import java.util.Map;

public class RdfClassMapping {
    /**
     * The class this mapping applies to
     */
    protected Class<?> clazz;

    Map<String, RdfPropertyMapping> propertyMappings;
}
