package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

final class RealBufferedSource
  implements BufferedSource
{
  public final Buffer buffer = new Buffer();
  boolean closed;
  public final Source source;

  RealBufferedSource(Source paramSource)
  {
    if (paramSource == null)
      throw new NullPointerException("source == null");
    this.source = paramSource;
  }

  public Buffer buffer()
  {
    return this.buffer;
  }

  public void close()
    throws IOException
  {
    if (this.closed)
      return;
    this.closed = true;
    this.source.close();
    this.buffer.clear();
  }

  public boolean exhausted()
    throws IOException
  {
    if (this.closed)
      throw new IllegalStateException("closed");
    return (this.buffer.exhausted()) && (this.source.read(this.buffer, 8192L) == -1L);
  }

  public long indexOf(byte paramByte)
    throws IOException
  {
    return indexOf(paramByte, 0L);
  }

  public long indexOf(byte paramByte, long paramLong)
    throws IOException
  {
    if (this.closed)
      throw new IllegalStateException("closed");
    do
    {
      Object localObject;
      paramLong = Math.max(paramLong, localObject);
      long l1 = this.buffer.indexOf(paramByte, paramLong);
      if (l1 != -1L)
        return l1;
      long l2 = this.buffer.size;
    }
    while (this.source.read(this.buffer, 8192L) != -1L);
    return -1L;
  }

  public long indexOf(ByteString paramByteString)
    throws IOException
  {
    return indexOf(paramByteString, 0L);
  }

  public long indexOf(ByteString paramByteString, long paramLong)
    throws IOException
  {
    if (this.closed)
      throw new IllegalStateException("closed");
    do
    {
      Object localObject;
      paramLong = Math.max(paramLong, 1L + (localObject - paramByteString.size()));
      long l1 = this.buffer.indexOf(paramByteString, paramLong);
      if (l1 != -1L)
        return l1;
      long l2 = this.buffer.size;
    }
    while (this.source.read(this.buffer, 8192L) != -1L);
    return -1L;
  }

  public long indexOfElement(ByteString paramByteString)
    throws IOException
  {
    return indexOfElement(paramByteString, 0L);
  }

  public long indexOfElement(ByteString paramByteString, long paramLong)
    throws IOException
  {
    if (this.closed)
      throw new IllegalStateException("closed");
    do
    {
      Object localObject;
      paramLong = Math.max(paramLong, localObject);
      long l1 = this.buffer.indexOfElement(paramByteString, paramLong);
      if (l1 != -1L)
        return l1;
      long l2 = this.buffer.size;
    }
    while (this.source.read(this.buffer, 8192L) != -1L);
    return -1L;
  }

  public InputStream inputStream()
  {
    return new InputStream()
    {
      public int available()
        throws IOException
      {
        if (RealBufferedSource.this.closed)
          throw new IOException("closed");
        return (int)Math.min(RealBufferedSource.this.buffer.size, 2147483647L);
      }

      public void close()
        throws IOException
      {
        RealBufferedSource.this.close();
      }

      public int read()
        throws IOException
      {
        if (RealBufferedSource.this.closed)
          throw new IOException("closed");
        if ((RealBufferedSource.this.buffer.size == 0L) && (RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 8192L) == -1L))
          return -1;
        return 0xFF & RealBufferedSource.this.buffer.readByte();
      }

      public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
        throws IOException
      {
        if (RealBufferedSource.this.closed)
          throw new IOException("closed");
        Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
        if ((RealBufferedSource.this.buffer.size == 0L) && (RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 8192L) == -1L))
          return -1;
        return RealBufferedSource.this.buffer.read(paramArrayOfByte, paramInt1, paramInt2);
      }

      public String toString()
      {
        return RealBufferedSource.this + ".inputStream()";
      }
    };
  }

  public boolean rangeEquals(long paramLong, ByteString paramByteString)
    throws IOException
  {
    return rangeEquals(paramLong, paramByteString, 0, paramByteString.size());
  }

  public boolean rangeEquals(long paramLong, ByteString paramByteString, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.closed)
      throw new IllegalStateException("closed");
    if ((paramLong < 0L) || (paramInt1 < 0) || (paramInt2 < 0) || (paramByteString.size() - paramInt1 < paramInt2))
      return false;
    for (int i = 0; ; i++)
    {
      if (i >= paramInt2)
        break label102;
      long l = paramLong + i;
      if ((!request(1L + l)) || (this.buffer.getByte(l) != paramByteString.getByte(paramInt1 + i)))
        break;
    }
    label102: return true;
  }

  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    if ((this.buffer.size == 0L) && (this.source.read(this.buffer, 8192L) == -1L))
      return -1;
    int i = (int)Math.min(paramInt2, this.buffer.size);
    return this.buffer.read(paramArrayOfByte, paramInt1, i);
  }

  public long read(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (paramBuffer == null)
      throw new IllegalArgumentException("sink == null");
    if (paramLong < 0L)
      throw new IllegalArgumentException("byteCount < 0: " + paramLong);
    if (this.closed)
      throw new IllegalStateException("closed");
    if ((this.buffer.size == 0L) && (this.source.read(this.buffer, 8192L) == -1L))
      return -1L;
    long l = Math.min(paramLong, this.buffer.size);
    return this.buffer.read(paramBuffer, l);
  }

  public long readAll(Sink paramSink)
    throws IOException
  {
    if (paramSink == null)
      throw new IllegalArgumentException("sink == null");
    long l1 = 0L;
    while (this.source.read(this.buffer, 8192L) != -1L)
    {
      long l2 = this.buffer.completeSegmentByteCount();
      if (l2 <= 0L)
        continue;
      l1 += l2;
      paramSink.write(this.buffer, l2);
    }
    if (this.buffer.size() > 0L)
    {
      l1 += this.buffer.size();
      paramSink.write(this.buffer, this.buffer.size());
    }
    return l1;
  }

  public byte readByte()
    throws IOException
  {
    require(1L);
    return this.buffer.readByte();
  }

  public byte[] readByteArray()
    throws IOException
  {
    this.buffer.writeAll(this.source);
    return this.buffer.readByteArray();
  }

  public byte[] readByteArray(long paramLong)
    throws IOException
  {
    require(paramLong);
    return this.buffer.readByteArray(paramLong);
  }

  public ByteString readByteString()
    throws IOException
  {
    this.buffer.writeAll(this.source);
    return this.buffer.readByteString();
  }

  public ByteString readByteString(long paramLong)
    throws IOException
  {
    require(paramLong);
    return this.buffer.readByteString(paramLong);
  }

  public long readDecimalLong()
    throws IOException
  {
    require(1L);
    for (int i = 0; request(i + 1); i++)
    {
      byte b = this.buffer.getByte(i);
      if (((b >= 48) && (b <= 57)) || ((i == 0) && (b == 45)))
        continue;
      if (i != 0)
        break;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Byte.valueOf(b);
      throw new NumberFormatException(String.format("Expected leading [0-9] or '-' character but was %#x", arrayOfObject));
    }
    return this.buffer.readDecimalLong();
  }

  public void readFully(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    try
    {
      require(paramLong);
      this.buffer.readFully(paramBuffer, paramLong);
      return;
    }
    catch (EOFException localEOFException)
    {
      paramBuffer.writeAll(this.buffer);
    }
    throw localEOFException;
  }

  public void readFully(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      require(paramArrayOfByte.length);
      this.buffer.readFully(paramArrayOfByte);
      return;
    }
    catch (EOFException localEOFException)
    {
      int i = 0;
      while (this.buffer.size > 0L)
      {
        int j = this.buffer.read(paramArrayOfByte, i, (int)this.buffer.size);
        if (j == -1)
          throw new AssertionError();
        i += j;
      }
    }
    throw localEOFException;
  }

  public long readHexadecimalUnsignedLong()
    throws IOException
  {
    require(1L);
    for (int i = 0; request(i + 1); i++)
    {
      byte b = this.buffer.getByte(i);
      if (((b >= 48) && (b <= 57)) || ((b >= 97) && (b <= 102)) || ((b >= 65) && (b <= 70)))
        continue;
      if (i != 0)
        break;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Byte.valueOf(b);
      throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", arrayOfObject));
    }
    return this.buffer.readHexadecimalUnsignedLong();
  }

  public int readInt()
    throws IOException
  {
    require(4L);
    return this.buffer.readInt();
  }

  public int readIntLe()
    throws IOException
  {
    require(4L);
    return this.buffer.readIntLe();
  }

  public long readLong()
    throws IOException
  {
    require(8L);
    return this.buffer.readLong();
  }

  public long readLongLe()
    throws IOException
  {
    require(8L);
    return this.buffer.readLongLe();
  }

  public short readShort()
    throws IOException
  {
    require(2L);
    return this.buffer.readShort();
  }

  public short readShortLe()
    throws IOException
  {
    require(2L);
    return this.buffer.readShortLe();
  }

  public String readString(long paramLong, Charset paramCharset)
    throws IOException
  {
    require(paramLong);
    if (paramCharset == null)
      throw new IllegalArgumentException("charset == null");
    return this.buffer.readString(paramLong, paramCharset);
  }

  public String readString(Charset paramCharset)
    throws IOException
  {
    if (paramCharset == null)
      throw new IllegalArgumentException("charset == null");
    this.buffer.writeAll(this.source);
    return this.buffer.readString(paramCharset);
  }

  public String readUtf8()
    throws IOException
  {
    this.buffer.writeAll(this.source);
    return this.buffer.readUtf8();
  }

  public String readUtf8(long paramLong)
    throws IOException
  {
    require(paramLong);
    return this.buffer.readUtf8(paramLong);
  }

  public int readUtf8CodePoint()
    throws IOException
  {
    require(1L);
    int i = this.buffer.getByte(0L);
    if ((i & 0xE0) == 192)
      require(2L);
    while (true)
    {
      return this.buffer.readUtf8CodePoint();
      if ((i & 0xF0) == 224)
      {
        require(3L);
        continue;
      }
      if ((i & 0xF8) != 240)
        continue;
      require(4L);
    }
  }

  public String readUtf8Line()
    throws IOException
  {
    long l = indexOf(10);
    if (l == -1L)
    {
      if (this.buffer.size != 0L)
        return readUtf8(this.buffer.size);
      return null;
    }
    return this.buffer.readUtf8Line(l);
  }

  public String readUtf8LineStrict()
    throws IOException
  {
    long l = indexOf(10);
    if (l == -1L)
    {
      Buffer localBuffer = new Buffer();
      this.buffer.copyTo(localBuffer, 0L, Math.min(32L, this.buffer.size()));
      throw new EOFException("\\n not found: size=" + this.buffer.size() + " content=" + localBuffer.readByteString().hex() + "…");
    }
    return this.buffer.readUtf8Line(l);
  }

  public boolean request(long paramLong)
    throws IOException
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("byteCount < 0: " + paramLong);
    if (this.closed)
      throw new IllegalStateException("closed");
    while (this.buffer.size < paramLong)
      if (this.source.read(this.buffer, 8192L) == -1L)
        return false;
    return true;
  }

  public void require(long paramLong)
    throws IOException
  {
    if (!request(paramLong))
      throw new EOFException();
  }

  public int select(Options paramOptions)
    throws IOException
  {
    if (this.closed)
      throw new IllegalStateException("closed");
    do
    {
      int i = this.buffer.selectPrefix(paramOptions);
      if (i == -1)
        return -1;
      int j = paramOptions.byteStrings[i].size();
      if (j > this.buffer.size)
        continue;
      this.buffer.skip(j);
      return i;
    }
    while (this.source.read(this.buffer, 8192L) != -1L);
    return -1;
  }

  public void skip(long paramLong)
    throws IOException
  {
    if (this.closed)
      throw new IllegalStateException("closed");
    while (true)
    {
      long l = Math.min(paramLong, this.buffer.size());
      this.buffer.skip(l);
      paramLong -= l;
      if (paramLong <= 0L)
        break;
      if ((this.buffer.size != 0L) || (this.source.read(this.buffer, 8192L) != -1L))
        continue;
      throw new EOFException();
    }
  }

  public Timeout timeout()
  {
    return this.source.timeout();
  }

  public String toString()
  {
    return "buffer(" + this.source + ")";
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     okio.RealBufferedSource
 * JD-Core Version:    0.6.0
 */