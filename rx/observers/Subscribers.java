package rx.observers;

import rx.Observer;
import rx.Subscriber;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action0;
import rx.functions.Action1;

public final class Subscribers
{
  private Subscribers()
  {
    throw new IllegalStateException("No instances!");
  }

  public static <T> Subscriber<T> create(Action1<? super T> paramAction1)
  {
    if (paramAction1 == null)
      throw new IllegalArgumentException("onNext can not be null");
    return new Subscriber(paramAction1)
    {
      public final void onCompleted()
      {
      }

      public final void onError(Throwable paramThrowable)
      {
        throw new OnErrorNotImplementedException(paramThrowable);
      }

      public final void onNext(T paramT)
      {
        this.val$onNext.call(paramT);
      }
    };
  }

  public static <T> Subscriber<T> create(Action1<? super T> paramAction1, Action1<Throwable> paramAction11)
  {
    if (paramAction1 == null)
      throw new IllegalArgumentException("onNext can not be null");
    if (paramAction11 == null)
      throw new IllegalArgumentException("onError can not be null");
    return new Subscriber(paramAction11, paramAction1)
    {
      public final void onCompleted()
      {
      }

      public final void onError(Throwable paramThrowable)
      {
        this.val$onError.call(paramThrowable);
      }

      public final void onNext(T paramT)
      {
        this.val$onNext.call(paramT);
      }
    };
  }

  public static <T> Subscriber<T> create(Action1<? super T> paramAction1, Action1<Throwable> paramAction11, Action0 paramAction0)
  {
    if (paramAction1 == null)
      throw new IllegalArgumentException("onNext can not be null");
    if (paramAction11 == null)
      throw new IllegalArgumentException("onError can not be null");
    if (paramAction0 == null)
      throw new IllegalArgumentException("onComplete can not be null");
    return new Subscriber(paramAction0, paramAction11, paramAction1)
    {
      public final void onCompleted()
      {
        this.val$onComplete.call();
      }

      public final void onError(Throwable paramThrowable)
      {
        this.val$onError.call(paramThrowable);
      }

      public final void onNext(T paramT)
      {
        this.val$onNext.call(paramT);
      }
    };
  }

  public static <T> Subscriber<T> empty()
  {
    return from(Observers.empty());
  }

  public static <T> Subscriber<T> from(Observer<? super T> paramObserver)
  {
    return new Subscriber(paramObserver)
    {
      public void onCompleted()
      {
        this.val$o.onCompleted();
      }

      public void onError(Throwable paramThrowable)
      {
        this.val$o.onError(paramThrowable);
      }

      public void onNext(T paramT)
      {
        this.val$o.onNext(paramT);
      }
    };
  }

  public static <T> Subscriber<T> wrap(Subscriber<? super T> paramSubscriber)
  {
    return new Subscriber(paramSubscriber, paramSubscriber)
    {
      public void onCompleted()
      {
        this.val$subscriber.onCompleted();
      }

      public void onError(Throwable paramThrowable)
      {
        this.val$subscriber.onError(paramThrowable);
      }

      public void onNext(T paramT)
      {
        this.val$subscriber.onNext(paramT);
      }
    };
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.observers.Subscribers
 * JD-Core Version:    0.6.0
 */