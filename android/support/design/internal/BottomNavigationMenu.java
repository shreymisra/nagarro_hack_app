package android.support.design.internal;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.MenuItem;
import android.view.SubMenu;

@RestrictTo({android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP})
public final class BottomNavigationMenu extends MenuBuilder
{
  public static final int MAX_ITEM_COUNT = 5;

  public BottomNavigationMenu(Context paramContext)
  {
    super(paramContext);
  }

  protected MenuItem addInternal(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    if (1 + size() > 5)
      throw new IllegalArgumentException("Maximum number of items supported by BottomNavigationView is 5. Limit can be checked with BottomNavigationView#getMaxItemCount()");
    stopDispatchingItemsChanged();
    MenuItem localMenuItem = super.addInternal(paramInt1, paramInt2, paramInt3, paramCharSequence);
    if ((localMenuItem instanceof MenuItemImpl))
      ((MenuItemImpl)localMenuItem).setExclusiveCheckable(true);
    startDispatchingItemsChanged();
    return localMenuItem;
  }

  public SubMenu addSubMenu(int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence)
  {
    throw new UnsupportedOperationException("BottomNavigationView does not support submenus");
  }
}

/* Location:           /home/satyam/AndroidStudioProjects/app/dex2jar-0.0.9.15/classes-dex2jar.jar
 * Qualified Name:     android.support.design.internal.BottomNavigationMenu
 * JD-Core Version:    0.6.0
 */