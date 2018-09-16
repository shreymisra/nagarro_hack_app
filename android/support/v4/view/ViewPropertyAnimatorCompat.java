package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;

public final class ViewPropertyAnimatorCompat
{
  static final int LISTENER_TAG_ID = 2113929216;
  private static final String TAG = "ViewAnimatorCompat";
  Runnable mEndAction = null;
  int mOldLayerType = -1;
  Runnable mStartAction = null;
  private WeakReference<View> mView;

  ViewPropertyAnimatorCompat(View paramView)
  {
    this.mView = new WeakReference(paramView);
  }

  private void setListenerInternal(View paramView, ViewPropertyAnimatorListener paramViewPropertyAnimatorListener)
  {
    if (paramViewPropertyAnimatorListener != null)
    {
      paramView.animate().setListener(new AnimatorListenerAdapter(paramViewPropertyAnimatorListener, paramView)
      {
        public void onAnimationCancel(Animator paramAnimator)
        {
          this.val$listener.onAnimationCancel(this.val$view);
        }

        public void onAnimationEnd(Animator paramAnimator)
        {
          this.val$listener.onAnimationEnd(this.val$view);
        }

        public void onAnimationStart(Animator paramAnimator)
        {
          this.val$listener.onAnimationStart(this.val$view);
        }
      });
      return;
    }
    paramView.animate().setListener(null);
  }

  public ViewPropertyAnimatorCompat alpha(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().alpha(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat alphaBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().alphaBy(paramFloat);
    return this;
  }

  public void cancel()
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().cancel();
  }

  public long getDuration()
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      return localView.animate().getDuration();
    return 0L;
  }

  public Interpolator getInterpolator()
  {
    View localView = (View)this.mView.get();
    if ((localView != null) && (Build.VERSION.SDK_INT >= 18))
      return (Interpolator)localView.animate().getInterpolator();
    return null;
  }

  public long getStartDelay()
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      return localView.animate().getStartDelay();
    return 0L;
  }

  public ViewPropertyAnimatorCompat rotation(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().rotation(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat rotationBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().rotationBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat rotationX(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().rotationX(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat rotationXBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().rotationXBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat rotationY(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().rotationY(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat rotationYBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().rotationYBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat scaleX(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().scaleX(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat scaleXBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().scaleXBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat scaleY(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().scaleY(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat scaleYBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().scaleYBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat setDuration(long paramLong)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().setDuration(paramLong);
    return this;
  }

  public ViewPropertyAnimatorCompat setInterpolator(Interpolator paramInterpolator)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().setInterpolator(paramInterpolator);
    return this;
  }

  public ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener paramViewPropertyAnimatorListener)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
    {
      if (Build.VERSION.SDK_INT >= 16)
        setListenerInternal(localView, paramViewPropertyAnimatorListener);
    }
    else
      return this;
    localView.setTag(2113929216, paramViewPropertyAnimatorListener);
    setListenerInternal(localView, new ViewPropertyAnimatorListenerApi14(this));
    return this;
  }

  public ViewPropertyAnimatorCompat setStartDelay(long paramLong)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().setStartDelay(paramLong);
    return this;
  }

  public ViewPropertyAnimatorCompat setUpdateListener(ViewPropertyAnimatorUpdateListener paramViewPropertyAnimatorUpdateListener)
  {
    View localView = (View)this.mView.get();
    if ((localView != null) && (Build.VERSION.SDK_INT >= 19))
    {
      2 local2 = null;
      if (paramViewPropertyAnimatorUpdateListener != null)
        local2 = new ValueAnimator.AnimatorUpdateListener(paramViewPropertyAnimatorUpdateListener, localView)
        {
          public void onAnimationUpdate(ValueAnimator paramValueAnimator)
          {
            this.val$listener.onAnimationUpdate(this.val$view);
          }
        };
      localView.animate().setUpdateListener(local2);
    }
    return this;
  }

  public void start()
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().start();
  }

  public ViewPropertyAnimatorCompat translationX(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().translationX(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat translationXBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().translationXBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat translationY(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().translationY(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat translationYBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().translationYBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat translationZ(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if ((localView != null) && (Build.VERSION.SDK_INT >= 21))
      localView.animate().translationZ(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat translationZBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if ((localView != null) && (Build.VERSION.SDK_INT >= 21))
      localView.animate().translationZBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat withEndAction(Runnable paramRunnable)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
    {
      if (Build.VERSION.SDK_INT >= 16)
        localView.animate().withEndAction(paramRunnable);
    }
    else
      return this;
    setListenerInternal(localView, new ViewPropertyAnimatorListenerApi14(this));
    this.mEndAction = paramRunnable;
    return this;
  }

  public ViewPropertyAnimatorCompat withLayer()
  {
    View localView = (View)this.mView.get();
    if (localView != null)
    {
      if (Build.VERSION.SDK_INT >= 16)
        localView.animate().withLayer();
    }
    else
      return this;
    this.mOldLayerType = localView.getLayerType();
    setListenerInternal(localView, new ViewPropertyAnimatorListenerApi14(this));
    return this;
  }

  public ViewPropertyAnimatorCompat withStartAction(Runnable paramRunnable)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
    {
      if (Build.VERSION.SDK_INT >= 16)
        localView.animate().withStartAction(paramRunnable);
    }
    else
      return this;
    setListenerInternal(localView, new ViewPropertyAnimatorListenerApi14(this));
    this.mStartAction = paramRunnable;
    return this;
  }

  public ViewPropertyAnimatorCompat x(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().x(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat xBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().xBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat y(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().y(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat yBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if (localView != null)
      localView.animate().yBy(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat z(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if ((localView != null) && (Build.VERSION.SDK_INT >= 21))
      localView.animate().z(paramFloat);
    return this;
  }

  public ViewPropertyAnimatorCompat zBy(float paramFloat)
  {
    View localView = (View)this.mView.get();
    if ((localView != null) && (Build.VERSION.SDK_INT >= 21))
      localView.animate().zBy(paramFloat);
    return this;
  }

  static class ViewPropertyAnimatorListenerApi14
    implements ViewPropertyAnimatorListener
  {
    boolean mAnimEndCalled;
    ViewPropertyAnimatorCompat mVpa;

    ViewPropertyAnimatorListenerApi14(ViewPropertyAnimatorCompat paramViewPropertyAnimatorCompat)
    {
      this.mVpa = paramViewPropertyAnimatorCompat;
    }

    public void onAnimationCancel(View paramView)
    {
      Object localObject = paramView.getTag(2113929216);
      boolean bool = localObject instanceof ViewPropertyAnimatorListener;
      ViewPropertyAnimatorListener localViewPropertyAnimatorListener = null;
      if (bool)
        localViewPropertyAnimatorListener = (ViewPropertyAnimatorListener)localObject;
      if (localViewPropertyAnimatorListener != null)
        localViewPropertyAnimatorListener.onAnimationCancel(paramView);
    }

    public void onAnimationEnd(View paramView)
    {
      if (this.mVpa.mOldLayerType > -1)
      {
        paramView.setLayerType(this.mVpa.mOldLayerType, null);
        this.mVpa.mOldLayerType = -1;
      }
      if ((Build.VERSION.SDK_INT >= 16) || (!this.mAnimEndCalled))
      {
        if (this.mVpa.mEndAction != null)
        {
          Runnable localRunnable = this.mVpa.mEndAction;
          this.mVpa.mEndAction = null;
          localRunnable.run();
        }
        Object localObject = paramView.getTag(2113929216);
        boolean bool = localObject instanceof ViewPropertyAnimatorListener;
        ViewPropertyAnimatorListener localViewPropertyAnimatorListener = null;
        if (bool)
          localViewPropertyAnimatorListener = (ViewPropertyAnimatorListener)localObject;
        if (localViewPropertyAnimatorListener != null)
          localViewPropertyAnimatorListener.onAnimationEnd(paramView);
        this.mAnimEndCalled = true;
      }
    }

    public void onAnimationStart(View paramView)
    {
      this.mAnimEndCalled = false;
      if (this.mVpa.mOldLayerType > -1)
        paramView.setLayerType(2, null);
      if (this.mVpa.mStartAction != null)
      {
        Runnable localRunnable = this.mVpa.mStartAction;
        this.mVpa.mStartAction = null;
        localRunnable.run();
      }
      Object localObject = paramView.getTag(2113929216);
      boolean bool = localObject instanceof ViewPropertyAnimatorListener;
      ViewPropertyAnimatorListener localViewPropertyAnimatorListener = null;
      if (bool)
        localViewPropertyAnimatorListener = (ViewPropertyAnimatorListener)localObject;
      if (localViewPropertyAnimatorListener != null)
        localViewPropertyAnimatorListener.onAnimationStart(paramView);
    }
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.ViewPropertyAnimatorCompat
 * JD-Core Version:    0.6.0
 */