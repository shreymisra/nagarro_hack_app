package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

final class Http2Writer
  implements Closeable
{
  private static final Logger logger = Logger.getLogger(Http2.class.getName());
  private final boolean client;
  private boolean closed;
  private final Buffer hpackBuffer;
  final Hpack.Writer hpackWriter;
  private int maxFrameSize;
  private final BufferedSink sink;

  public Http2Writer(BufferedSink paramBufferedSink, boolean paramBoolean)
  {
    this.sink = paramBufferedSink;
    this.client = paramBoolean;
    this.hpackBuffer = new Buffer();
    this.hpackWriter = new Hpack.Writer(this.hpackBuffer);
    this.maxFrameSize = 16384;
  }

  private void writeContinuationFrames(int paramInt, long paramLong)
    throws IOException
  {
    if (paramLong > 0L)
    {
      int i = (int)Math.min(this.maxFrameSize, paramLong);
      paramLong -= i;
      if (paramLong == 0L);
      for (byte b = 4; ; b = 0)
      {
        frameHeader(paramInt, i, 9, b);
        this.sink.write(this.hpackBuffer, i);
        break;
      }
    }
  }

  private static void writeMedium(BufferedSink paramBufferedSink, int paramInt)
    throws IOException
  {
    paramBufferedSink.writeByte(0xFF & paramInt >>> 16);
    paramBufferedSink.writeByte(0xFF & paramInt >>> 8);
    paramBufferedSink.writeByte(paramInt & 0xFF);
  }

  public void applyAndAckSettings(Settings paramSettings)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    this.maxFrameSize = paramSettings.getMaxFrameSize(this.maxFrameSize);
    if (paramSettings.getHeaderTableSize() != -1)
      this.hpackWriter.setHeaderTableSizeSetting(paramSettings.getHeaderTableSize());
    frameHeader(0, 0, 4, 1);
    this.sink.flush();
    monitorexit;
  }

  public void close()
    throws IOException
  {
    monitorenter;
    try
    {
      this.closed = true;
      this.sink.close();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void connectionPreface()
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    boolean bool = this.client;
    if (!bool);
    while (true)
    {
      monitorexit;
      return;
      if (logger.isLoggable(Level.FINE))
      {
        Logger localLogger = logger;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Http2.CONNECTION_PREFACE.hex();
        localLogger.fine(Util.format(">> CONNECTION %s", arrayOfObject));
      }
      this.sink.write(Http2.CONNECTION_PREFACE.toByteArray());
      this.sink.flush();
    }
  }

  public void data(boolean paramBoolean, int paramInt1, Buffer paramBuffer, int paramInt2)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    byte b = 0;
    if (paramBoolean)
      b = (byte)1;
    dataFrame(paramInt1, b, paramBuffer, paramInt2);
    monitorexit;
  }

  void dataFrame(int paramInt1, byte paramByte, Buffer paramBuffer, int paramInt2)
    throws IOException
  {
    frameHeader(paramInt1, paramInt2, 0, paramByte);
    if (paramInt2 > 0)
      this.sink.write(paramBuffer, paramInt2);
  }

  public void flush()
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    this.sink.flush();
    monitorexit;
  }

  public void frameHeader(int paramInt1, int paramInt2, byte paramByte1, byte paramByte2)
    throws IOException
  {
    if (logger.isLoggable(Level.FINE))
      logger.fine(Http2.frameLog(false, paramInt1, paramInt2, paramByte1, paramByte2));
    if (paramInt2 > this.maxFrameSize)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(this.maxFrameSize);
      arrayOfObject2[1] = Integer.valueOf(paramInt2);
      throw Http2.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", arrayOfObject2);
    }
    if ((0x80000000 & paramInt1) != 0)
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(paramInt1);
      throw Http2.illegalArgument("reserved bit set: %s", arrayOfObject1);
    }
    writeMedium(this.sink, paramInt2);
    this.sink.writeByte(paramByte1 & 0xFF);
    this.sink.writeByte(paramByte2 & 0xFF);
    this.sink.writeInt(0x7FFFFFFF & paramInt1);
  }

  public void goAway(int paramInt, ErrorCode paramErrorCode, byte[] paramArrayOfByte)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    if (paramErrorCode.httpCode == -1)
      throw Http2.illegalArgument("errorCode.httpCode == -1", new Object[0]);
    frameHeader(0, 8 + paramArrayOfByte.length, 7, 0);
    this.sink.writeInt(paramInt);
    this.sink.writeInt(paramErrorCode.httpCode);
    if (paramArrayOfByte.length > 0)
      this.sink.write(paramArrayOfByte);
    this.sink.flush();
    monitorexit;
  }

  public void headers(int paramInt, List<Header> paramList)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    headers(false, paramInt, paramList);
    monitorexit;
  }

  void headers(boolean paramBoolean, int paramInt, List<Header> paramList)
    throws IOException
  {
    if (this.closed)
      throw new IOException("closed");
    this.hpackWriter.writeHeaders(paramList);
    long l = this.hpackBuffer.size();
    int i = (int)Math.min(this.maxFrameSize, l);
    if (l == i);
    for (byte b = 4; ; b = 0)
    {
      if (paramBoolean)
        b = (byte)(b | 0x1);
      frameHeader(paramInt, i, 1, b);
      this.sink.write(this.hpackBuffer, i);
      if (l > i)
        writeContinuationFrames(paramInt, l - i);
      return;
    }
  }

  public int maxDataLength()
  {
    return this.maxFrameSize;
  }

  public void ping(boolean paramBoolean, int paramInt1, int paramInt2)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    if (paramBoolean);
    for (byte b = 1; ; b = 0)
    {
      frameHeader(0, 8, 6, b);
      this.sink.writeInt(paramInt1);
      this.sink.writeInt(paramInt2);
      this.sink.flush();
      monitorexit;
      return;
    }
  }

  public void pushPromise(int paramInt1, int paramInt2, List<Header> paramList)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    this.hpackWriter.writeHeaders(paramList);
    long l = this.hpackBuffer.size();
    int i = (int)Math.min(-4 + this.maxFrameSize, l);
    if (l == i);
    for (byte b = 4; ; b = 0)
    {
      frameHeader(paramInt1, i + 4, 5, b);
      this.sink.writeInt(0x7FFFFFFF & paramInt2);
      this.sink.write(this.hpackBuffer, i);
      if (l > i)
        writeContinuationFrames(paramInt1, l - i);
      monitorexit;
      return;
    }
  }

  public void rstStream(int paramInt, ErrorCode paramErrorCode)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    if (paramErrorCode.httpCode == -1)
      throw new IllegalArgumentException();
    frameHeader(paramInt, 4, 3, 0);
    this.sink.writeInt(paramErrorCode.httpCode);
    this.sink.flush();
    monitorexit;
  }

  public void settings(Settings paramSettings)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    frameHeader(0, 6 * paramSettings.size(), 4, 0);
    int i = 0;
    if (i < 10)
      if (paramSettings.isSet(i))
        break label105;
    while (true)
    {
      this.sink.writeShort(j);
      this.sink.writeInt(paramSettings.get(i));
      break label99;
      this.sink.flush();
      monitorexit;
      return;
      label99: i++;
      break;
      label105: int j = i;
      if (j == 4)
      {
        j = 3;
        continue;
      }
      if (j != 7)
        continue;
      j = 4;
    }
  }

  public void synReply(boolean paramBoolean, int paramInt, List<Header> paramList)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    headers(paramBoolean, paramInt, paramList);
    monitorexit;
  }

  public void synStream(boolean paramBoolean, int paramInt1, int paramInt2, List<Header> paramList)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    headers(paramBoolean, paramInt1, paramList);
    monitorexit;
  }

  public void windowUpdate(int paramInt, long paramLong)
    throws IOException
  {
    monitorenter;
    try
    {
      if (this.closed)
        throw new IOException("closed");
    }
    finally
    {
      monitorexit;
    }
    if ((paramLong == 0L) || (paramLong > 2147483647L))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(paramLong);
      throw Http2.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", arrayOfObject);
    }
    frameHeader(paramInt, 4, 8, 0);
    this.sink.writeInt((int)paramLong);
    this.sink.flush();
    monitorexit;
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     okhttp3.internal.http2.Http2Writer
 * JD-Core Version:    0.6.0
 */