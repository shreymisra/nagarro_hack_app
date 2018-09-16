package android.support.v4.text;

import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

@RequiresApi(21)
class ICUCompatApi21
{
  private static final String TAG = "ICUCompatApi21";
  private static Method sAddLikelySubtagsMethod;

  static
  {
    try
    {
      sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", new Class[] { Locale.class });
      return;
    }
    catch (Exception localException)
    {
    }
    throw new IllegalStateException(localException);
  }

  public static String maximizeAndGetScript(Locale paramLocale)
  {
    try
    {
      Object[] arrayOfObject = { paramLocale };
      String str = ((Locale)sAddLikelySubtagsMethod.invoke(null, arrayOfObject)).getScript();
      return str;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Log.w("ICUCompatApi21", localInvocationTargetException);
      return paramLocale.getScript();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        Log.w("ICUCompatApi21", localIllegalAccessException);
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.text.ICUCompatApi21
 * JD-Core Version:    0.6.0
 */