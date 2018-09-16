package me.zhanghai.android.materialprogressbar;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract interface TintableDrawable
{
  public abstract void setTint(@ColorInt int paramInt);

  public abstract void setTintList(@Nullable ColorStateList paramColorStateList);

  public abstract void setTintMode(@NonNull PorterDuff.Mode paramMode);
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     me.zhanghai.android.materialprogressbar.TintableDrawable
 * JD-Core Version:    0.6.0
 */