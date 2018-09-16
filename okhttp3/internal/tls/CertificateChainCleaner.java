package okhttp3.internal.tls;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.X509TrustManager;
import okhttp3.internal.platform.Platform;

public abstract class CertificateChainCleaner
{
  public static CertificateChainCleaner get(X509TrustManager paramX509TrustManager)
  {
    return Platform.get().buildCertificateChainCleaner(paramX509TrustManager);
  }

  public static CertificateChainCleaner get(X509Certificate[] paramArrayOfX509Certificate)
  {
    return new BasicCertificateChainCleaner(TrustRootIndex.get(paramArrayOfX509Certificate));
  }

  public abstract List<Certificate> clean(List<Certificate> paramList, String paramString)
    throws SSLPeerUnverifiedException;
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     okhttp3.internal.tls.CertificateChainCleaner
 * JD-Core Version:    0.6.0
 */