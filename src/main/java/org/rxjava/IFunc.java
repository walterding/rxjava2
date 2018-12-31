package org.rxjava;

/**
 * Created by hinotohui on 18/12/31.
 */
public interface IFunc<U,V> {
    V call(U u);
}
