package okhttp3.internal.cache;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.internal.Internal;
import okhttp3.internal.http.HttpDate;
import okhttp3.internal.http.HttpHeaders;

public final class CacheStrategy
{
  public final Response cacheResponse;
  public final Request networkRequest;

  CacheStrategy(Request paramRequest, Response paramResponse)
  {
    this.networkRequest = paramRequest;
    this.cacheResponse = paramResponse;
  }

  public static boolean isCacheable(Response paramResponse, Request paramRequest)
  {
    switch (paramResponse.code())
    {
    default:
    case 302:
    case 307:
    case 200:
    case 203:
    case 204:
    case 300:
    case 301:
    case 308:
    case 404:
    case 405:
    case 410:
    case 414:
    case 501:
    }
    do
      return false;
    while (((paramResponse.header("Expires") == null) && (paramResponse.cacheControl().maxAgeSeconds() == -1) && (!paramResponse.cacheControl().isPublic()) && (!paramResponse.cacheControl().isPrivate())) || (paramResponse.cacheControl().noStore()) || (paramRequest.cacheControl().noStore()));
    return true;
  }

  public static class Factory
  {
    private int ageSeconds = -1;
    final Response cacheResponse;
    private String etag;
    private Date expires;
    private Date lastModified;
    private String lastModifiedString;
    final long nowMillis;
    private long receivedResponseMillis;
    final Request request;
    private long sentRequestMillis;
    private Date servedDate;
    private String servedDateString;

    public Factory(long paramLong, Request paramRequest, Response paramResponse)
    {
      this.nowMillis = paramLong;
      this.request = paramRequest;
      this.cacheResponse = paramResponse;
      if (paramResponse != null)
      {
        this.sentRequestMillis = paramResponse.sentRequestAtMillis();
        this.receivedResponseMillis = paramResponse.receivedResponseAtMillis();
        Headers localHeaders = paramResponse.headers();
        int i = 0;
        int j = localHeaders.size();
        if (i < j)
        {
          String str1 = localHeaders.name(i);
          String str2 = localHeaders.value(i);
          if ("Date".equalsIgnoreCase(str1))
          {
            this.servedDate = HttpDate.parse(str2);
            this.servedDateString = str2;
          }
          while (true)
          {
            i++;
            break;
            if ("Expires".equalsIgnoreCase(str1))
            {
              this.expires = HttpDate.parse(str2);
              continue;
            }
            if ("Last-Modified".equalsIgnoreCase(str1))
            {
              this.lastModified = HttpDate.parse(str2);
              this.lastModifiedString = str2;
              continue;
            }
            if ("ETag".equalsIgnoreCase(str1))
            {
              this.etag = str2;
              continue;
            }
            if (!"Age".equalsIgnoreCase(str1))
              continue;
            this.ageSeconds = HttpHeaders.parseSeconds(str2, -1);
          }
        }
      }
    }

    private long cacheResponseAge()
    {
      long l1 = 0L;
      if (this.servedDate != null)
        l1 = Math.max(l1, this.receivedResponseMillis - this.servedDate.getTime());
      long l2;
      if (this.ageSeconds != -1)
        l2 = Math.max(l1, TimeUnit.SECONDS.toMillis(this.ageSeconds));
      while (true)
      {
        long l3 = this.receivedResponseMillis - this.sentRequestMillis;
        return this.nowMillis - this.receivedResponseMillis + (l2 + l3);
        l2 = l1;
      }
    }

    private long computeFreshnessLifetime()
    {
      long l1 = 0L;
      CacheControl localCacheControl = this.cacheResponse.cacheControl();
      if (localCacheControl.maxAgeSeconds() != -1)
        l1 = TimeUnit.SECONDS.toMillis(localCacheControl.maxAgeSeconds());
      label86: 
      do
      {
        return l1;
        if (this.expires == null)
          continue;
        long l4;
        long l5;
        if (this.servedDate != null)
        {
          l4 = this.servedDate.getTime();
          l5 = this.expires.getTime() - l4;
          if (l5 <= l1)
            break label86;
        }
        while (true)
        {
          return l5;
          l4 = this.receivedResponseMillis;
          break;
          l5 = l1;
        }
      }
      while ((this.lastModified == null) || (this.cacheResponse.request().url().query() != null));
      long l2;
      if (this.servedDate != null)
        l2 = this.servedDate.getTime();
      while (true)
      {
        long l3 = l2 - this.lastModified.getTime();
        if (l3 <= l1)
          break;
        return l3 / 10L;
        l2 = this.sentRequestMillis;
      }
    }

    private CacheStrategy getCandidate()
    {
      if (this.cacheResponse == null)
        return new CacheStrategy(this.request, null);
      if ((this.request.isHttps()) && (this.cacheResponse.handshake() == null))
        return new CacheStrategy(this.request, null);
      if (!CacheStrategy.isCacheable(this.cacheResponse, this.request))
        return new CacheStrategy(this.request, null);
      CacheControl localCacheControl1 = this.request.cacheControl();
      if ((localCacheControl1.noCache()) || (hasConditions(this.request)))
        return new CacheStrategy(this.request, null);
      long l1 = cacheResponseAge();
      long l2 = computeFreshnessLifetime();
      if (localCacheControl1.maxAgeSeconds() != -1)
        l2 = Math.min(l2, TimeUnit.SECONDS.toMillis(localCacheControl1.maxAgeSeconds()));
      long l3 = 0L;
      if (localCacheControl1.minFreshSeconds() != -1)
        l3 = TimeUnit.SECONDS.toMillis(localCacheControl1.minFreshSeconds());
      long l4 = 0L;
      CacheControl localCacheControl2 = this.cacheResponse.cacheControl();
      if ((!localCacheControl2.mustRevalidate()) && (localCacheControl1.maxStaleSeconds() != -1))
        l4 = TimeUnit.SECONDS.toMillis(localCacheControl1.maxStaleSeconds());
      if ((!localCacheControl2.noCache()) && (l1 + l3 < l2 + l4))
      {
        Response.Builder localBuilder1 = this.cacheResponse.newBuilder();
        if (l1 + l3 >= l2)
          localBuilder1.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
        if ((l1 > 86400000L) && (isFreshnessLifetimeHeuristic()))
          localBuilder1.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
        return new CacheStrategy(null, localBuilder1.build());
      }
      String str1;
      String str2;
      if (this.etag != null)
      {
        str1 = "If-None-Match";
        str2 = this.etag;
      }
      while (true)
      {
        Headers.Builder localBuilder = this.request.headers().newBuilder();
        Internal.instance.addLenient(localBuilder, str1, str2);
        Request localRequest = this.request.newBuilder().headers(localBuilder.build()).build();
        CacheStrategy localCacheStrategy = new CacheStrategy(localRequest, this.cacheResponse);
        return localCacheStrategy;
        if (this.lastModified != null)
        {
          str1 = "If-Modified-Since";
          str2 = this.lastModifiedString;
          continue;
        }
        if (this.servedDate == null)
          break;
        str1 = "If-Modified-Since";
        str2 = this.servedDateString;
      }
      return new CacheStrategy(this.request, null);
    }

    private static boolean hasConditions(Request paramRequest)
    {
      return (paramRequest.header("If-Modified-Since") != null) || (paramRequest.header("If-None-Match") != null);
    }

    private boolean isFreshnessLifetimeHeuristic()
    {
      return (this.cacheResponse.cacheControl().maxAgeSeconds() == -1) && (this.expires == null);
    }

    public CacheStrategy get()
    {
      CacheStrategy localCacheStrategy = getCandidate();
      if ((localCacheStrategy.networkRequest != null) && (this.request.cacheControl().onlyIfCached()))
        localCacheStrategy = new CacheStrategy(null, null);
      return localCacheStrategy;
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     okhttp3.internal.cache.CacheStrategy
 * JD-Core Version:    0.6.0
 */