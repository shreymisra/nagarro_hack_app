package rx.internal.schedulers;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import rx.Subscription;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action0;
import rx.internal.util.SubscriptionList;
import rx.plugins.RxJavaHooks;
import rx.subscriptions.CompositeSubscription;

public final class ScheduledAction extends AtomicReference<Thread>
  implements Runnable, Subscription
{
  private static final long serialVersionUID = -3962399486978279857L;
  final Action0 action;
  final SubscriptionList cancel;

  public ScheduledAction(Action0 paramAction0)
  {
    this.action = paramAction0;
    this.cancel = new SubscriptionList();
  }

  public ScheduledAction(Action0 paramAction0, SubscriptionList paramSubscriptionList)
  {
    this.action = paramAction0;
    this.cancel = new SubscriptionList(new Remover2(this, paramSubscriptionList));
  }

  public ScheduledAction(Action0 paramAction0, CompositeSubscription paramCompositeSubscription)
  {
    this.action = paramAction0;
    this.cancel = new SubscriptionList(new Remover(this, paramCompositeSubscription));
  }

  public void add(Future<?> paramFuture)
  {
    this.cancel.add(new FutureCompleter(paramFuture));
  }

  public void add(Subscription paramSubscription)
  {
    this.cancel.add(paramSubscription);
  }

  public void addParent(SubscriptionList paramSubscriptionList)
  {
    this.cancel.add(new Remover2(this, paramSubscriptionList));
  }

  public void addParent(CompositeSubscription paramCompositeSubscription)
  {
    this.cancel.add(new Remover(this, paramCompositeSubscription));
  }

  public boolean isUnsubscribed()
  {
    return this.cancel.isUnsubscribed();
  }

  public void run()
  {
    try
    {
      lazySet(Thread.currentThread());
      this.action.call();
      return;
    }
    catch (OnErrorNotImplementedException localOnErrorNotImplementedException)
    {
      signalError(new IllegalStateException("Exception thrown on Scheduler.Worker thread. Add `onError` handling.", localOnErrorNotImplementedException));
      return;
    }
    catch (Throwable localThrowable)
    {
      signalError(new IllegalStateException("Fatal Exception thrown on Scheduler.Worker thread.", localThrowable));
      return;
    }
    finally
    {
      unsubscribe();
    }
    throw localObject;
  }

  void signalError(Throwable paramThrowable)
  {
    RxJavaHooks.onError(paramThrowable);
    Thread localThread = Thread.currentThread();
    localThread.getUncaughtExceptionHandler().uncaughtException(localThread, paramThrowable);
  }

  public void unsubscribe()
  {
    if (!this.cancel.isUnsubscribed())
      this.cancel.unsubscribe();
  }

  final class FutureCompleter
    implements Subscription
  {
    private final Future<?> f;

    FutureCompleter()
    {
      Object localObject;
      this.f = localObject;
    }

    public boolean isUnsubscribed()
    {
      return this.f.isCancelled();
    }

    public void unsubscribe()
    {
      if (ScheduledAction.this.get() != Thread.currentThread())
      {
        this.f.cancel(true);
        return;
      }
      this.f.cancel(false);
    }
  }

  static final class Remover extends AtomicBoolean
    implements Subscription
  {
    private static final long serialVersionUID = 247232374289553518L;
    final CompositeSubscription parent;
    final ScheduledAction s;

    public Remover(ScheduledAction paramScheduledAction, CompositeSubscription paramCompositeSubscription)
    {
      this.s = paramScheduledAction;
      this.parent = paramCompositeSubscription;
    }

    public boolean isUnsubscribed()
    {
      return this.s.isUnsubscribed();
    }

    public void unsubscribe()
    {
      if (compareAndSet(false, true))
        this.parent.remove(this.s);
    }
  }

  static final class Remover2 extends AtomicBoolean
    implements Subscription
  {
    private static final long serialVersionUID = 247232374289553518L;
    final SubscriptionList parent;
    final ScheduledAction s;

    public Remover2(ScheduledAction paramScheduledAction, SubscriptionList paramSubscriptionList)
    {
      this.s = paramScheduledAction;
      this.parent = paramSubscriptionList;
    }

    public boolean isUnsubscribed()
    {
      return this.s.isUnsubscribed();
    }

    public void unsubscribe()
    {
      if (compareAndSet(false, true))
        this.parent.remove(this.s);
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.schedulers.ScheduledAction
 * JD-Core Version:    0.6.0
 */