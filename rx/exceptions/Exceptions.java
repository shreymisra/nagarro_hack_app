package rx.exceptions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import rx.Observer;
import rx.SingleSubscriber;
import rx.annotations.Beta;

public final class Exceptions
{
  private static final int MAX_DEPTH = 25;

  private Exceptions()
  {
    throw new IllegalStateException("No instances!");
  }

  public static void addCause(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    HashSet localHashSet = new HashSet();
    int j;
    for (int i = 0; ; i = j)
    {
      if (paramThrowable1.getCause() != null)
      {
        j = i + 1;
        if (i >= 25)
          return;
        paramThrowable1 = paramThrowable1.getCause();
        if (!localHashSet.contains(paramThrowable1.getCause()));
      }
      else
      {
        try
        {
          paramThrowable1.initCause(paramThrowable2);
          return;
        }
        catch (Throwable localThrowable)
        {
          return;
        }
      }
      localHashSet.add(paramThrowable1.getCause());
    }
  }

  public static Throwable getFinalCause(Throwable paramThrowable)
  {
    int j;
    for (int i = 0; ; i = j)
    {
      if (paramThrowable.getCause() != null)
      {
        j = i + 1;
        if (i >= 25)
          paramThrowable = new RuntimeException("Stack too deep to get final cause");
      }
      else
      {
        return paramThrowable;
      }
      paramThrowable = paramThrowable.getCause();
    }
  }

  public static RuntimeException propagate(Throwable paramThrowable)
  {
    if ((paramThrowable instanceof RuntimeException))
      throw ((RuntimeException)paramThrowable);
    if ((paramThrowable instanceof Error))
      throw ((Error)paramThrowable);
    throw new RuntimeException(paramThrowable);
  }

  public static void throwIfAny(List<? extends Throwable> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      if (paramList.size() == 1)
      {
        Throwable localThrowable = (Throwable)paramList.get(0);
        if ((localThrowable instanceof RuntimeException))
          throw ((RuntimeException)localThrowable);
        if ((localThrowable instanceof Error))
          throw ((Error)localThrowable);
        throw new RuntimeException(localThrowable);
      }
      throw new CompositeException(paramList);
    }
  }

  public static void throwIfFatal(Throwable paramThrowable)
  {
    if ((paramThrowable instanceof OnErrorNotImplementedException))
      throw ((OnErrorNotImplementedException)paramThrowable);
    if ((paramThrowable instanceof OnErrorFailedException))
      throw ((OnErrorFailedException)paramThrowable);
    if ((paramThrowable instanceof OnCompletedFailedException))
      throw ((OnCompletedFailedException)paramThrowable);
    if ((paramThrowable instanceof VirtualMachineError))
      throw ((VirtualMachineError)paramThrowable);
    if ((paramThrowable instanceof ThreadDeath))
      throw ((ThreadDeath)paramThrowable);
    if ((paramThrowable instanceof LinkageError))
      throw ((LinkageError)paramThrowable);
  }

  @Beta
  public static void throwOrReport(Throwable paramThrowable, Observer<?> paramObserver)
  {
    throwIfFatal(paramThrowable);
    paramObserver.onError(paramThrowable);
  }

  @Beta
  public static void throwOrReport(Throwable paramThrowable, Observer<?> paramObserver, Object paramObject)
  {
    throwIfFatal(paramThrowable);
    paramObserver.onError(OnErrorThrowable.addValueAsLastCause(paramThrowable, paramObject));
  }

  @Beta
  public static void throwOrReport(Throwable paramThrowable, SingleSubscriber<?> paramSingleSubscriber)
  {
    throwIfFatal(paramThrowable);
    paramSingleSubscriber.onError(paramThrowable);
  }

  @Beta
  public static void throwOrReport(Throwable paramThrowable, SingleSubscriber<?> paramSingleSubscriber, Object paramObject)
  {
    throwIfFatal(paramThrowable);
    paramSingleSubscriber.onError(OnErrorThrowable.addValueAsLastCause(paramThrowable, paramObject));
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.exceptions.Exceptions
 * JD-Core Version:    0.6.0
 */