package rx.internal.util.atomic;

public final class SpscLinkedAtomicQueue<E> extends BaseLinkedAtomicQueue<E>
{
  public SpscLinkedAtomicQueue()
  {
    LinkedQueueNode localLinkedQueueNode = new LinkedQueueNode();
    spProducerNode(localLinkedQueueNode);
    spConsumerNode(localLinkedQueueNode);
    localLinkedQueueNode.soNext(null);
  }

  public boolean offer(E paramE)
  {
    if (paramE == null)
      throw new NullPointerException("null elements not allowed");
    LinkedQueueNode localLinkedQueueNode = new LinkedQueueNode(paramE);
    lpProducerNode().soNext(localLinkedQueueNode);
    spProducerNode(localLinkedQueueNode);
    return true;
  }

  public E peek()
  {
    LinkedQueueNode localLinkedQueueNode = lpConsumerNode().lvNext();
    if (localLinkedQueueNode != null)
      return localLinkedQueueNode.lpValue();
    return null;
  }

  public E poll()
  {
    LinkedQueueNode localLinkedQueueNode = lpConsumerNode().lvNext();
    if (localLinkedQueueNode != null)
    {
      Object localObject = localLinkedQueueNode.getAndNullValue();
      spConsumerNode(localLinkedQueueNode);
      return localObject;
    }
    return null;
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     rx.internal.util.atomic.SpscLinkedAtomicQueue
 * JD-Core Version:    0.6.0
 */