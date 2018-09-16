package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

public final class OnSubscribeRefCount<T>
  implements Observable.OnSubscribe<T>
{
  volatile CompositeSubscription baseSubscription = new CompositeSubscription();
  final ReentrantLock lock = new ReentrantLock();
  private final ConnectableObservable<? extends T> source;
  final AtomicInteger subscriptionCount = new AtomicInteger(0);

  public OnSubscribeRefCount(ConnectableObservable<? extends T> paramConnectableObservable)
  {
    this.source = paramConnectableObservable;
  }

  private Subscription disconnect(CompositeSubscription paramCompositeSubscription)
  {
    return Subscriptions.create(new Action0(paramCompositeSubscription)
    {
      public void call()
      {
        OnSubscribeRefCount.this.lock.lock();
        try
        {
          if ((OnSubscribeRefCount.this.baseSubscription == this.val$current) && (OnSubscribeRefCount.this.subscriptionCount.decrementAndGet() == 0))
          {
            OnSubscribeRefCount.this.baseSubscription.unsubscribe();
            OnSubscribeRefCount.this.baseSubscription = new CompositeSubscription();
          }
          return;
        }
        finally
        {
          OnSubscribeRefCount.this.lock.unlock();
        }
        throw localObject;
      }
    });
  }

  private Action1<Subscription> onSubscribe(Subscriber<? super T> paramSubscriber, AtomicBoolean paramAtomicBoolean)
  {
    return new Action1(paramSubscriber, paramAtomicBoolean)
    {
      public void call(Subscription paramSubscription)
      {
        try
        {
          OnSubscribeRefCount.this.baseSubscription.add(paramSubscription);
          OnSubscribeRefCount.this.doSubscribe(this.val$subscriber, OnSubscribeRefCount.this.baseSubscription);
          return;
        }
        finally
        {
          OnSubscribeRefCount.this.lock.unlock();
          this.val$writeLocked.set(false);
        }
        throw localObject;
      }
    };
  }

  public void call(Subscriber<? super T> paramSubscriber)
  {
    this.lock.lock();
    if (this.subscriptionCount.incrementAndGet() == 1)
    {
      AtomicBoolean localAtomicBoolean = new AtomicBoolean(true);
      try
      {
        this.source.connect(onSubscribe(paramSubscriber, localAtomicBoolean));
        return;
      }
      finally
      {
        if (localAtomicBoolean.get())
          this.lock.unlock();
      }
    }
    try
    {
      doSubscribe(paramSubscriber, this.baseSubscription);
      return;
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject2;
  }

  void doSubscribe(Subscriber<? super T> paramSubscriber, CompositeSubscription paramCompositeSubscription)
  {
    paramSubscriber.add(disconnect(paramCompositeSubscription));
    this.source.unsafeSubscribe(new Subscriber(paramSubscriber, paramSubscriber, paramCompositeSubscription)
    {
      void cleanup()
      {
        OnSubscribeRefCount.this.lock.lock();
        try
        {
          if (OnSubscribeRefCount.this.baseSubscription == this.val$currentBase)
          {
            OnSubscribeRefCount.this.baseSubscription.unsubscribe();
            OnSubscribeRefCount.this.baseSubscription = new CompositeSubscription();
            OnSubscribeRefCount.this.subscriptionCount.set(0);
          }
          return;
        }
        finally
        {
          OnSubscribeRefCount.this.lock.unlock();
        }
        throw localObject;
      }

      public void onCompleted()
      {
        cleanup();
        this.val$subscriber.onCompleted();
      }

      public void onError(Throwable paramThrowable)
      {
        cleanup();
        this.val$subscriber.onError(paramThrowable);
      }

      public void onNext(T paramT)
      {
        this.val$subscriber.onNext(paramT);
      }
    });
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.operators.OnSubscribeRefCount
 * JD-Core Version:    0.6.0
 */