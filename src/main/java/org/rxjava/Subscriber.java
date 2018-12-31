package org.rxjava;

/**
 * Created by hinotohui on 18/12/31.
 */
public interface Subscriber<T> {
    public void onNext(T t);
    public void onComplete();
    public void onError(Throwable t);
}