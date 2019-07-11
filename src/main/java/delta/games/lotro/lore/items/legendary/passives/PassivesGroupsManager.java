package delta.games.lotro.lore.items.legendary.passives;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manager for passives groups.
 * @author DAM
 */
public class PassivesGroupsManager
{
  private Map<Integer,PassivesGroup> _passivesByItem;

  /**
   * Constructor.
   */
  public PassivesGroupsManager()
  {
    _passivesByItem=new HashMap<Integer,PassivesGroup>();
  }

  /**
   * Add a passives group.
   * @param group Group to add.
   */
  public void addPassivesGroup(PassivesGroup group)
  {
    for(Integer itemId : group.getItemIds())
    {
      _passivesByItem.put(itemId,group);
    }
  }

  /**
   * Get the passives for an item.
   * @param itemId Item identifier.
   * @return A possibly empty but never <code>null</code> set of passive identifiers.
   */
  public Set<Integer> getPassiveIdsForItem(int itemId)
  {
    Set<Integer> ret=new HashSet<Integer>();
    PassivesGroup group=_passivesByItem.get(Integer.valueOf(itemId));
    if (group!=null)
    {
      ret.addAll(group.getPassiveIds());
    }
    return ret;
  }
}
