package delta.games.lotro.character.status.relics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Relics inventory for a single character.
 * @author DAM
 */
public class RelicsInventory
{
  private Map<Integer,Integer> _relicsCount;

  /**
   * Constructor.
   */
  public RelicsInventory()
  {
    _relicsCount=new HashMap<Integer,Integer>();
  }

  /**
   * Reset inventory.
   */
  public void clear()
  {
    _relicsCount.clear();
  }

  /**
   * Get the managed relics identifiers.
   * @return A sorted list of relic identifiers.
   */
  public List<Integer> getRelicIdentifiers()
  {
    List<Integer> ret=new ArrayList<Integer>(_relicsCount.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get the relic count for a single relic.
   * @param relicId Relic identifier.
   * @return A count.
   */
  public int getRelicCount(int relicId)
  {
    Integer count=_relicsCount.get(Integer.valueOf(relicId));
    return (count!=null)?count.intValue():0;
  }

  /**
   * Set the relics count for a given relic.
   * @param relicId Relic identifier.
   * @param relicCount Relic count to set.
   */
  public void setRelicCount(int relicId, int relicCount)
  {
    _relicsCount.put(Integer.valueOf(relicId),Integer.valueOf(relicCount));
  }
}
