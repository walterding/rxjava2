package org.rxjava;

/**
 * Created by hinotohui on 18/12/31.
 */
public class OperatorMap<U,V> implements Operator<V,U> {
    private IFunc<? super U,? extends V> transform;

    public OperatorMap(IFunc<? super U, ? extends V> transform) {
        this.transform = transform;
    }

    public Subscriber<U> operate(final Subscriber<? super V> subscriber) {
        return new Subscriber<U>() {

            public void onNext(U u) {
                subscriber.onNext(transform.call(u));
            }


            public void onComplete() {
            }

            public void onError(Throwable t) {

            }
        };
    }
}