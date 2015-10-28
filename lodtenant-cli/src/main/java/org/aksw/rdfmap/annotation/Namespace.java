package org.aksw.rdfmap.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Namespace {
//    String ns();
//    String iri();
    String value();
}
