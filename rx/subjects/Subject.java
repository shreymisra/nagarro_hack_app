package rx.subjects;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;

public abstract class Subject<T, R> extends Observable<R>
  implements Observer<T>
{
  protected Subject(Observable.OnSubscribe<R> paramOnSubscribe)
  {
    super(paramOnSubscribe);
  }

  public abstract boolean hasObservers();

  public final SerializedSubject<T, R> toSerialized()
  {
    if (getClass() == SerializedSubject.class)
      return (SerializedSubject)this;
    return new SerializedSubject(this);
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.subjects.Subject
 * JD-Core Version:    0.6.0
 */