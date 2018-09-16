package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

@RequiresApi(19)
class AccessibilityNodeProviderCompatKitKat
{
  public static Object newAccessibilityNodeProviderBridge(AccessibilityNodeInfoBridge paramAccessibilityNodeInfoBridge)
  {
    return new AccessibilityNodeProvider(paramAccessibilityNodeInfoBridge)
    {
      public AccessibilityNodeInfo createAccessibilityNodeInfo(int paramInt)
      {
        return (AccessibilityNodeInfo)this.val$bridge.createAccessibilityNodeInfo(paramInt);
      }

      public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String paramString, int paramInt)
      {
        return this.val$bridge.findAccessibilityNodeInfosByText(paramString, paramInt);
      }

      public AccessibilityNodeInfo findFocus(int paramInt)
      {
        return (AccessibilityNodeInfo)this.val$bridge.findFocus(paramInt);
      }

      public boolean performAction(int paramInt1, int paramInt2, Bundle paramBundle)
      {
        return this.val$bridge.performAction(paramInt1, paramInt2, paramBundle);
      }
    };
  }

  static abstract interface AccessibilityNodeInfoBridge
  {
    public abstract Object createAccessibilityNodeInfo(int paramInt);

    public abstract List<Object> findAccessibilityNodeInfosByText(String paramString, int paramInt);

    public abstract Object findFocus(int paramInt);

    public abstract boolean performAction(int paramInt1, int paramInt2, Bundle paramBundle);
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.v4.view.accessibility.AccessibilityNodeProviderCompatKitKat
 * JD-Core Version:    0.6.0
 */