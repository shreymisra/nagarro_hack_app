package rx.internal.operators;

import java.util.concurrent.atomic.AtomicInteger;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.observers.Subscribers;

public final class OnSubscribeAutoConnect<T> extends AtomicInteger
  implements Observable.OnSubscribe<T>
{
  final Action1<? super Subscription> connection;
  final int numberOfSubscribers;
  final ConnectableObservable<? extends T> source;

  public OnSubscribeAutoConnect(ConnectableObservable<? extends T> paramConnectableObservable, int paramInt, Action1<? super Subscription> paramAction1)
  {
    if (paramInt <= 0)
      throw new IllegalArgumentException("numberOfSubscribers > 0 required");
    this.source = paramConnectableObservable;
    this.numberOfSubscribers = paramInt;
    this.connection = paramAction1;
  }

  public void call(Subscriber<? super T> paramSubscriber)
  {
    this.source.unsafeSubscribe(Subscribers.wrap(paramSubscriber));
    if (incrementAndGet() == this.numberOfSubscribers)
      this.source.connect(this.connection);
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.operators.OnSubscribeAutoConnect
 * JD-Core Version:    0.6.0
 */