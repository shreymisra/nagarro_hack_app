package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

public final class CompoundButtonCompat
{
  private static final CompoundButtonCompatBaseImpl IMPL;

  static
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      IMPL = new CompoundButtonCompatApi23Impl();
      return;
    }
    if (Build.VERSION.SDK_INT >= 21)
    {
      IMPL = new CompoundButtonCompatApi21Impl();
      return;
    }
    IMPL = new CompoundButtonCompatBaseImpl();
  }

  @Nullable
  public static Drawable getButtonDrawable(@NonNull CompoundButton paramCompoundButton)
  {
    return IMPL.getButtonDrawable(paramCompoundButton);
  }

  @Nullable
  public static ColorStateList getButtonTintList(@NonNull CompoundButton paramCompoundButton)
  {
    return IMPL.getButtonTintList(paramCompoundButton);
  }

  @Nullable
  public static PorterDuff.Mode getButtonTintMode(@NonNull CompoundButton paramCompoundButton)
  {
    return IMPL.getButtonTintMode(paramCompoundButton);
  }

  public static void setButtonTintList(@NonNull CompoundButton paramCompoundButton, @Nullable ColorStateList paramColorStateList)
  {
    IMPL.setButtonTintList(paramCompoundButton, paramColorStateList);
  }

  public static void setButtonTintMode(@NonNull CompoundButton paramCompoundButton, @Nullable PorterDuff.Mode paramMode)
  {
    IMPL.setButtonTintMode(paramCompoundButton, paramMode);
  }

  @RequiresApi(21)
  static class CompoundButtonCompatApi21Impl extends CompoundButtonCompat.CompoundButtonCompatBaseImpl
  {
    public ColorStateList getButtonTintList(CompoundButton paramCompoundButton)
    {
      return paramCompoundButton.getButtonTintList();
    }

    public PorterDuff.Mode getButtonTintMode(CompoundButton paramCompoundButton)
    {
      return paramCompoundButton.getButtonTintMode();
    }

    public void setButtonTintList(CompoundButton paramCompoundButton, ColorStateList paramColorStateList)
    {
      paramCompoundButton.setButtonTintList(paramColorStateList);
    }

    public void setButtonTintMode(CompoundButton paramCompoundButton, PorterDuff.Mode paramMode)
    {
      paramCompoundButton.setButtonTintMode(paramMode);
    }
  }

  @RequiresApi(23)
  static class CompoundButtonCompatApi23Impl extends CompoundButtonCompat.CompoundButtonCompatApi21Impl
  {
    public Drawable getButtonDrawable(CompoundButton paramCompoundButton)
    {
      return paramCompoundButton.getButtonDrawable();
    }
  }

  static class CompoundButtonCompatBaseImpl
  {
    private static final String TAG = "CompoundButtonCompat";
    private static Field sButtonDrawableField;
    private static boolean sButtonDrawableFieldFetched;

    public Drawable getButtonDrawable(CompoundButton paramCompoundButton)
    {
      if (!sButtonDrawableFieldFetched);
      try
      {
        sButtonDrawableField = CompoundButton.class.getDeclaredField("mButtonDrawable");
        sButtonDrawableField.setAccessible(true);
        sButtonDrawableFieldFetched = true;
        if (sButtonDrawableField == null);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        try
        {
          Drawable localDrawable = (Drawable)sButtonDrawableField.get(paramCompoundButton);
          return localDrawable;
          localNoSuchFieldException = localNoSuchFieldException;
          Log.i("CompoundButtonCompat", "Failed to retrieve mButtonDrawable field", localNoSuchFieldException);
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          Log.i("CompoundButtonCompat", "Failed to get button drawable via reflection", localIllegalAccessException);
          sButtonDrawableField = null;
        }
      }
      return null;
    }

    public ColorStateList getButtonTintList(CompoundButton paramCompoundButton)
    {
      if ((paramCompoundButton instanceof TintableCompoundButton))
        return ((TintableCompoundButton)paramCompoundButton).getSupportButtonTintList();
      return null;
    }

    public PorterDuff.Mode getButtonTintMode(CompoundButton paramCompoundButton)
    {
      if ((paramCompoundButton instanceof TintableCompoundButton))
        return ((TintableCompoundButton)paramCompoundButton).getSupportButtonTintMode();
      return null;
    }

    public void setButtonTintList(CompoundButton paramCompoundButton, ColorStateList paramColorStateList)
    {
      if ((paramCompoundButton instanceof TintableCompoundButton))
        ((TintableCompoundButton)paramCompoundButton).setSupportButtonTintList(paramColorStateList);
    }

    public void setButtonTintMode(CompoundButton paramCompoundButton, PorterDuff.Mode paramMode)
    {
      if ((paramCompoundButton instanceof TintableCompoundButton))
        ((TintableCompoundButton)paramCompoundButton).setSupportButtonTintMode(paramMode);
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.CompoundButtonCompat
 * JD-Core Version:    0.6.0
 */