package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.webStore.WebStoreItem;

/**
 * Filter for achievables of a given contents pack.
 * @param <T> Type of managed achievables.
 * @author DAM
 */
public class WebStoreItemFilter<T extends Achievable> implements Filter<T>
{
  private WebStoreItem _contentsPack;

  /**
   * Constructor.
   * @param contentsPack Contents pack to select (may be <code>null</code>).
   */
  public WebStoreItemFilter(WebStoreItem contentsPack)
  {
    _contentsPack=contentsPack;
  }

  /**
   * Get the contents pack to use.
   * @return A contents pack or <code>null</code>.
   */
  public WebStoreItem getContentsPack()
  {
    return _contentsPack;
  }

  /**
   * Set the contents pack to select.
   * @param contentsPack Contents pack size to use, may be <code>null</code>.
   */
  public void setContentsPack(WebStoreItem contentsPack)
  {
    _contentsPack=contentsPack;
  }

  @Override
  public boolean accept(T achievable)
  {
    if (_contentsPack==null)
    {
      return true;
    }
    WebStoreItem contentsPack=achievable.getWebStoreItem();
    return (_contentsPack==contentsPack);
  }
}
