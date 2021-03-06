package okio;

final class Segment
{
  static final int SHARE_MINIMUM = 1024;
  static final int SIZE = 8192;
  final byte[] data;
  int limit;
  Segment next;
  boolean owner;
  int pos;
  Segment prev;
  boolean shared;

  Segment()
  {
    this.data = new byte[8192];
    this.owner = true;
    this.shared = false;
  }

  Segment(Segment paramSegment)
  {
    this(paramSegment.data, paramSegment.pos, paramSegment.limit);
    paramSegment.shared = true;
  }

  Segment(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.data = paramArrayOfByte;
    this.pos = paramInt1;
    this.limit = paramInt2;
    this.owner = false;
    this.shared = true;
  }

  public void compact()
  {
    if (this.prev == this)
      throw new IllegalStateException();
    if (!this.prev.owner);
    while (true)
    {
      return;
      int i = this.limit - this.pos;
      int j = 8192 - this.prev.limit;
      if (this.prev.shared);
      for (int k = 0; i <= j + k; k = this.prev.pos)
      {
        writeTo(this.prev, i);
        pop();
        SegmentPool.recycle(this);
        return;
      }
    }
  }

  public Segment pop()
  {
    if (this.next != this);
    for (Segment localSegment = this.next; ; localSegment = null)
    {
      this.prev.next = this.next;
      this.next.prev = this.prev;
      this.next = null;
      this.prev = null;
      return localSegment;
    }
  }

  public Segment push(Segment paramSegment)
  {
    paramSegment.prev = this;
    paramSegment.next = this.next;
    this.next.prev = paramSegment;
    this.next = paramSegment;
    return paramSegment;
  }

  public Segment split(int paramInt)
  {
    if ((paramInt <= 0) || (paramInt > this.limit - this.pos))
      throw new IllegalArgumentException();
    Segment localSegment;
    if (paramInt >= 1024)
      localSegment = new Segment(this);
    while (true)
    {
      localSegment.limit = (paramInt + localSegment.pos);
      this.pos = (paramInt + this.pos);
      this.prev.push(localSegment);
      return localSegment;
      localSegment = SegmentPool.take();
      System.arraycopy(this.data, this.pos, localSegment.data, 0, paramInt);
    }
  }

  public void writeTo(Segment paramSegment, int paramInt)
  {
    if (!paramSegment.owner)
      throw new IllegalArgumentException();
    if (paramInt + paramSegment.limit > 8192)
    {
      if (paramSegment.shared)
        throw new IllegalArgumentException();
      if (paramInt + paramSegment.limit - paramSegment.pos > 8192)
        throw new IllegalArgumentException();
      System.arraycopy(paramSegment.data, paramSegment.pos, paramSegment.data, 0, paramSegment.limit - paramSegment.pos);
      paramSegment.limit -= paramSegment.pos;
      paramSegment.pos = 0;
    }
    System.arraycopy(this.data, this.pos, paramSegment.data, paramSegment.limit, paramInt);
    paramSegment.limit = (paramInt + paramSegment.limit);
    this.pos = (paramInt + this.pos);
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     okio.Segment
 * JD-Core Version:    0.6.0
 */