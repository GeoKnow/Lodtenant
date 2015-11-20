package org.aksw.lodtenant.cli;

import com.google.common.base.Function;

public interface EntityResolver<K, V>
    extends Function<K, V>
{
}
