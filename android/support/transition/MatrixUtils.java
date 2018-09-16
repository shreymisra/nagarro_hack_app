package android.support.transition;

import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;

class MatrixUtils
{
  static final Matrix IDENTITY_MATRIX = new Matrix()
  {
    void oops()
    {
      throw new IllegalStateException("Matrix can not be modified");
    }

    public boolean postConcat(Matrix paramMatrix)
    {
      oops();
      return false;
    }

    public boolean postRotate(float paramFloat)
    {
      oops();
      return false;
    }

    public boolean postRotate(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      oops();
      return false;
    }

    public boolean postScale(float paramFloat1, float paramFloat2)
    {
      oops();
      return false;
    }

    public boolean postScale(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      oops();
      return false;
    }

    public boolean postSkew(float paramFloat1, float paramFloat2)
    {
      oops();
      return false;
    }

    public boolean postSkew(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      oops();
      return false;
    }

    public boolean postTranslate(float paramFloat1, float paramFloat2)
    {
      oops();
      return false;
    }

    public boolean preConcat(Matrix paramMatrix)
    {
      oops();
      return false;
    }

    public boolean preRotate(float paramFloat)
    {
      oops();
      return false;
    }

    public boolean preRotate(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      oops();
      return false;
    }

    public boolean preScale(float paramFloat1, float paramFloat2)
    {
      oops();
      return false;
    }

    public boolean preScale(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      oops();
      return false;
    }

    public boolean preSkew(float paramFloat1, float paramFloat2)
    {
      oops();
      return false;
    }

    public boolean preSkew(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      oops();
      return false;
    }

    public boolean preTranslate(float paramFloat1, float paramFloat2)
    {
      oops();
      return false;
    }

    public void reset()
    {
      oops();
    }

    public void set(Matrix paramMatrix)
    {
      oops();
    }

    public boolean setConcat(Matrix paramMatrix1, Matrix paramMatrix2)
    {
      oops();
      return false;
    }

    public boolean setPolyToPoly(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, int paramInt3)
    {
      oops();
      return false;
    }

    public boolean setRectToRect(RectF paramRectF1, RectF paramRectF2, Matrix.ScaleToFit paramScaleToFit)
    {
      oops();
      return false;
    }

    public void setRotate(float paramFloat)
    {
      oops();
    }

    public void setRotate(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      oops();
    }

    public void setScale(float paramFloat1, float paramFloat2)
    {
      oops();
    }

    public void setScale(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      oops();
    }

    public void setSinCos(float paramFloat1, float paramFloat2)
    {
      oops();
    }

    public void setSinCos(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      oops();
    }

    public void setSkew(float paramFloat1, float paramFloat2)
    {
      oops();
    }

    public void setSkew(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      oops();
    }

    public void setTranslate(float paramFloat1, float paramFloat2)
    {
      oops();
    }

    public void setValues(float[] paramArrayOfFloat)
    {
      oops();
    }
  };
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.transition.MatrixUtils
 * JD-Core Version:    0.6.0
 */