package org.rxjava;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/**
 * Created by hinotohui on 18/12/31.
 */
public class Observable {

    private OnSubscribe onSubscribe;
    private Subscriber subscriber;

    public <V> Observable schedule(final ExecutorService asyncPool){

        return new Observable(new OnSubscribe<V>() {
            public void call(final Subscriber<? super V> subscriber) {
                asyncPool.submit(new Runnable() {
                    public void run() {
                        Observable.this.onSubscribe.call(subscriber);
                    }
                });
            }
        });
    }

    public <U,V> Observable lift(final Observable preObservable,
                                 final Operator<V, U>
                                         operator){
        return new Observable(new OnSubscribe<V>() {
            public void call(Subscriber<? super V> subscriber) {
                preObservable.onSubscribe.call(operator
                        .operate(subscriber));
            }
        });
    }

    public <U,V> Observable map(IFunc<U,V> fun){
        Observable observable = lift(this,new OperatorMap<U,V>(fun));
        return observable;
    }

    public <T> void subscribe(Subscriber<T> subscriber){
        if (onSubscribe!=null)
            onSubscribe.call(subscriber);
    }

    private Observable(OnSubscribe onSubscribe){
        this.onSubscribe=onSubscribe;
    }

    public interface OnSubscribe<T>{
        void call(Subscriber<? super T> subscriber);
    }

    public static <T> Observable create(OnSubscribe<T> onSubscribe){
        return new Observable(onSubscribe);
    }

    public static void main(String[] args){
        Observable.create(new OnSubscribe<String>() {

            public void call(Subscriber<? super String> subscriber) {
                String update = "update";

                subscriber.onNext(update);
                subscriber.onNext(update);
            }
        }). map(new IFunc<String,String>() {
            public String call(String o) {
                return o+"123";
            }
        }).subscribe(new Subscriber<String>() {

            public void onNext(String o) {
                System.out.println(o);
                onComplete();
            }

            public void onComplete() {
                System.out.println("end");
            }
            public void onError(Throwable t) {

            }
        });

    }
}
