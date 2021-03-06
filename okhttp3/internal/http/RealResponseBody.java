package okhttp3.internal.http;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public final class RealResponseBody extends ResponseBody
{
  private final Headers headers;
  private final BufferedSource source;

  public RealResponseBody(Headers paramHeaders, BufferedSource paramBufferedSource)
  {
    this.headers = paramHeaders;
    this.source = paramBufferedSource;
  }

  public long contentLength()
  {
    return HttpHeaders.contentLength(this.headers);
  }

  public MediaType contentType()
  {
    String str = this.headers.get("Content-Type");
    if (str != null)
      return MediaType.parse(str);
    return null;
  }

  public BufferedSource source()
  {
    return this.source;
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     okhttp3.internal.http.RealResponseBody
 * JD-Core Version:    0.6.0
 */