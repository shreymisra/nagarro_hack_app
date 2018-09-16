package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

@RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
public abstract interface TintableImageSourceView
{
  @Nullable
  public abstract ColorStateList getSupportImageTintList();

  @Nullable
  public abstract PorterDuff.Mode getSupportImageTintMode();

  public abstract void setSupportImageTintList(@Nullable ColorStateList paramColorStateList);

  public abstract void setSupportImageTintMode(@Nullable PorterDuff.Mode paramMode);
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.TintableImageSourceView
 * JD-Core Version:    0.6.0
 */