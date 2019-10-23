package delta.games.lotro.lore.items.legendary.passives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manager for passives groups.
 * @author DAM
 */
public class PassivesGroupsManager
{
  private Map<Integer,List<PassivesGroup>> _passivesByItem;

  /**
   * Constructor.
   */
  public PassivesGroupsManager()
  {
    _passivesByItem=new HashMap<Integer,List<PassivesGroup>>();
  }

  /**
   * Add a passives group.
   * @param group Group to add.
   */
  public void addPassivesGroup(PassivesGroup group)
  {
    for(Integer itemId : group.getItemIds())
    {
      List<PassivesGroup> groups=_passivesByItem.get(itemId);
      if (groups==null)
      {
        groups=new ArrayList<PassivesGroup>();
        _passivesByItem.put(itemId,groups);
      }
      groups.add(group);
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
    List<PassivesGroup> groups=_passivesByItem.get(Integer.valueOf(itemId));
    if (groups!=null)
    {
      for(PassivesGroup group : groups)
      {
        ret.addAll(group.getPassiveIds());
      }
    }
    return ret;
  }
}
