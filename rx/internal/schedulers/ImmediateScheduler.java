package rx.internal.schedulers;

import java.util.concurrent.TimeUnit;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.BooleanSubscription;
import rx.subscriptions.Subscriptions;

public final class ImmediateScheduler extends Scheduler
{
  public static final ImmediateScheduler INSTANCE = new ImmediateScheduler();

  public Scheduler.Worker createWorker()
  {
    return new InnerImmediateScheduler();
  }

  final class InnerImmediateScheduler extends Scheduler.Worker
    implements Subscription
  {
    final BooleanSubscription innerSubscription = new BooleanSubscription();

    InnerImmediateScheduler()
    {
    }

    public boolean isUnsubscribed()
    {
      return this.innerSubscription.isUnsubscribed();
    }

    public Subscription schedule(Action0 paramAction0)
    {
      paramAction0.call();
      return Subscriptions.unsubscribed();
    }

    public Subscription schedule(Action0 paramAction0, long paramLong, TimeUnit paramTimeUnit)
    {
      return schedule(new SleepingAction(paramAction0, this, ImmediateScheduler.this.now() + paramTimeUnit.toMillis(paramLong)));
    }

    public void unsubscribe()
    {
      this.innerSubscription.unsubscribe();
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.schedulers.ImmediateScheduler
 * JD-Core Version:    0.6.0
 */