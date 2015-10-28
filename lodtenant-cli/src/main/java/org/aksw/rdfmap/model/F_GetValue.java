package org.aksw.rdfmap.model;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import com.google.common.base.Function;

public class F_GetValue<T>
    implements Function<Object, T>
{
    protected Class<T> clazz;
    protected Expression expression;
    protected EvaluationContext evalContext;

    public F_GetValue(Class<T> clazz, Expression expression,
            EvaluationContext evalContext) {
        super();
        this.clazz = clazz;
        this.expression = expression;
        this.evalContext = evalContext;
    }

    @Override
    public T apply(Object arg) {
        T result = expression.getValue(evalContext, arg, clazz);
        return result;
    }
}
