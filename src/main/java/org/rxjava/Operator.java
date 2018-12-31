package org.rxjava;

/**
 * Created by hinotohui on 18/12/31.
 */
public interface Operator <U,V>{
    public Subscriber<V> operate(final Subscriber<? super U> subscriber);
}
