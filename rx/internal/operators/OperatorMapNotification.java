package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable.Operator;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.functions.Func1;

public final class OperatorMapNotification<T, R>
  implements Observable.Operator<R, T>
{
  final Func0<? extends R> onCompleted;
  final Func1<? super Throwable, ? extends R> onError;
  final Func1<? super T, ? extends R> onNext;

  public OperatorMapNotification(Func1<? super T, ? extends R> paramFunc1, Func1<? super Throwable, ? extends R> paramFunc11, Func0<? extends R> paramFunc0)
  {
    this.onNext = paramFunc1;
    this.onError = paramFunc11;
    this.onCompleted = paramFunc0;
  }

  public Subscriber<? super T> call(Subscriber<? super R> paramSubscriber)
  {
    MapNotificationSubscriber localMapNotificationSubscriber = new MapNotificationSubscriber(paramSubscriber, this.onNext, this.onError, this.onCompleted);
    paramSubscriber.add(localMapNotificationSubscriber);
    paramSubscriber.setProducer(new Producer(localMapNotificationSubscriber)
    {
      public void request(long paramLong)
      {
        this.val$parent.requestInner(paramLong);
      }
    });
    return localMapNotificationSubscriber;
  }

  static final class MapNotificationSubscriber<T, R> extends Subscriber<T>
  {
    static final long COMPLETED_FLAG = -9223372036854775808L;
    static final long REQUESTED_MASK = 9223372036854775807L;
    final Subscriber<? super R> actual;
    final AtomicLong missedRequested;
    final Func0<? extends R> onCompleted;
    final Func1<? super Throwable, ? extends R> onError;
    final Func1<? super T, ? extends R> onNext;
    long produced;
    final AtomicReference<Producer> producer;
    final AtomicLong requested;
    R value;

    public MapNotificationSubscriber(Subscriber<? super R> paramSubscriber, Func1<? super T, ? extends R> paramFunc1, Func1<? super Throwable, ? extends R> paramFunc11, Func0<? extends R> paramFunc0)
    {
      this.actual = paramSubscriber;
      this.onNext = paramFunc1;
      this.onError = paramFunc11;
      this.onCompleted = paramFunc0;
      this.requested = new AtomicLong();
      this.missedRequested = new AtomicLong();
      this.producer = new AtomicReference();
    }

    void accountProduced()
    {
      long l = this.produced;
      if ((l != 0L) && (this.producer.get() != null))
        BackpressureUtils.produced(this.requested, l);
    }

    public void onCompleted()
    {
      accountProduced();
      try
      {
        this.value = this.onCompleted.call();
        tryEmit();
        return;
      }
      catch (Throwable localThrowable)
      {
        while (true)
          Exceptions.throwOrReport(localThrowable, this.actual);
      }
    }

    public void onError(Throwable paramThrowable)
    {
      accountProduced();
      try
      {
        this.value = this.onError.call(paramThrowable);
        tryEmit();
        return;
      }
      catch (Throwable localThrowable)
      {
        while (true)
          Exceptions.throwOrReport(localThrowable, this.actual, paramThrowable);
      }
    }

    public void onNext(T paramT)
    {
      try
      {
        this.produced = (1L + this.produced);
        this.actual.onNext(this.onNext.call(paramT));
        return;
      }
      catch (Throwable localThrowable)
      {
        Exceptions.throwOrReport(localThrowable, this.actual, paramT);
      }
    }

    void requestInner(long paramLong)
    {
      if (paramLong < 0L)
        throw new IllegalArgumentException("n >= 0 required but it was " + paramLong);
      if (paramLong == 0L);
      Producer localProducer2;
      long l3;
      do
      {
        do
        {
          return;
          long l1;
          long l2;
          do
          {
            while (true)
            {
              l1 = this.requested.get();
              if ((0x0 & l1) != 0L)
              {
                long l4 = l1 & 0xFFFFFFFF;
                long l5 = 0x0 | BackpressureUtils.addCap(l4, paramLong);
                if (!this.requested.compareAndSet(l1, l5))
                  continue;
                if (l4 != 0L)
                  break;
                if (!this.actual.isUnsubscribed())
                  this.actual.onNext(this.value);
                if (this.actual.isUnsubscribed())
                  break;
                this.actual.onCompleted();
                return;
              }
            }
            l2 = BackpressureUtils.addCap(l1, paramLong);
          }
          while (!this.requested.compareAndSet(l1, l2));
          AtomicReference localAtomicReference = this.producer;
          Producer localProducer1 = (Producer)localAtomicReference.get();
          if (localProducer1 != null)
          {
            localProducer1.request(paramLong);
            return;
          }
          BackpressureUtils.getAndAddRequest(this.missedRequested, paramLong);
          localProducer2 = (Producer)localAtomicReference.get();
        }
        while (localProducer2 == null);
        l3 = this.missedRequested.getAndSet(0L);
      }
      while (l3 == 0L);
      localProducer2.request(l3);
    }

    public void setProducer(Producer paramProducer)
    {
      if (this.producer.compareAndSet(null, paramProducer))
      {
        long l = this.missedRequested.getAndSet(0L);
        if (l != 0L)
          paramProducer.request(l);
        return;
      }
      throw new IllegalStateException("Producer already set!");
    }

    void tryEmit()
    {
      long l = this.requested.get();
      if ((l & 0x0) != 0L);
      do
      {
        do
        {
          return;
          if (!this.requested.compareAndSet(l, l | 0x0))
            break;
        }
        while ((l == 0L) && (this.producer.get() != null));
        if (this.actual.isUnsubscribed())
          continue;
        this.actual.onNext(this.value);
      }
      while (this.actual.isUnsubscribed());
      this.actual.onCompleted();
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.operators.OperatorMapNotification
 * JD-Core Version:    0.6.0
 */