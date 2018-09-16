package rx.internal.operators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Completable.OnSubscribe;
import rx.CompletableSubscriber;
import rx.Subscription;
import rx.subscriptions.SerialSubscription;
import rx.subscriptions.Subscriptions;

public final class CompletableOnSubscribeConcatIterable
  implements Completable.OnSubscribe
{
  final Iterable<? extends Completable> sources;

  public CompletableOnSubscribeConcatIterable(Iterable<? extends Completable> paramIterable)
  {
    this.sources = paramIterable;
  }

  public void call(CompletableSubscriber paramCompletableSubscriber)
  {
    Iterator localIterator;
    try
    {
      localIterator = this.sources.iterator();
      if (localIterator == null)
      {
        paramCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
        paramCompletableSubscriber.onError(new NullPointerException("The iterator returned is null"));
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      paramCompletableSubscriber.onSubscribe(Subscriptions.unsubscribed());
      paramCompletableSubscriber.onError(localThrowable);
      return;
    }
    ConcatInnerSubscriber localConcatInnerSubscriber = new ConcatInnerSubscriber(paramCompletableSubscriber, localIterator);
    paramCompletableSubscriber.onSubscribe(localConcatInnerSubscriber.sd);
    localConcatInnerSubscriber.next();
  }

  static final class ConcatInnerSubscriber extends AtomicInteger
    implements CompletableSubscriber
  {
    private static final long serialVersionUID = -7965400327305809232L;
    final CompletableSubscriber actual;
    final SerialSubscription sd;
    final Iterator<? extends Completable> sources;

    public ConcatInnerSubscriber(CompletableSubscriber paramCompletableSubscriber, Iterator<? extends Completable> paramIterator)
    {
      this.actual = paramCompletableSubscriber;
      this.sources = paramIterator;
      this.sd = new SerialSubscription();
    }

    void next()
    {
      if (this.sd.isUnsubscribed());
      label10: 
      do
      {
        Iterator localIterator;
        do
        {
          return;
          break label10;
          continue;
          while (getAndIncrement() != 0);
          localIterator = this.sources;
        }
        while (this.sd.isUnsubscribed());
        try
        {
          boolean bool = localIterator.hasNext();
          if (!bool)
          {
            this.actual.onCompleted();
            return;
          }
        }
        catch (Throwable localThrowable1)
        {
          this.actual.onError(localThrowable1);
          return;
        }
        Completable localCompletable;
        try
        {
          localCompletable = (Completable)localIterator.next();
          if (localCompletable == null)
          {
            this.actual.onError(new NullPointerException("The completable returned is null"));
            return;
          }
        }
        catch (Throwable localThrowable2)
        {
          this.actual.onError(localThrowable2);
          return;
        }
        localCompletable.unsafeSubscribe(this);
      }
      while (decrementAndGet() != 0);
    }

    public void onCompleted()
    {
      next();
    }

    public void onError(Throwable paramThrowable)
    {
      this.actual.onError(paramThrowable);
    }

    public void onSubscribe(Subscription paramSubscription)
    {
      this.sd.set(paramSubscription);
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.operators.CompletableOnSubscribeConcatIterable
 * JD-Core Version:    0.6.0
 */