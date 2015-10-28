package org.aksw.rdfmap.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RdfGen {
    String p();
    String o();
}
