package delta.games.lotro.lore.items.cosmetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Storage for item cosmetics data.
 * @author DAM
 */
public class ItemCosmetics
{
  private Map<Integer,int[]> _cosmeticIDToItemIDs;

  /**
   * Constructor.
   */
  public ItemCosmetics()
  {
    _cosmeticIDToItemIDs=new HashMap<Integer,int[]>();
  }

  /**
   * Register a cosmetics entry.
   * @param itemIDs Related item IDs.
   */
  public void addEntry(int[] itemIDs)
  {
    int cosmeticID=_cosmeticIDToItemIDs.size();
    _cosmeticIDToItemIDs.put(Integer.valueOf(cosmeticID),itemIDs);
  }

  /**
   * Get the cosmetic IDs.
   * @return A list of cosmetic IDs.
   */
  public List<Integer> getCosmeticsIDs()
  {
    List<Integer> ids=new ArrayList<Integer>(_cosmeticIDToItemIDs.keySet());
    Collections.sort(ids);
    return ids;
  }

  /**
   * Find the cosmetic ID for an item.
   * @param itemID Item identifier.
   * @return A cosmetic ID or <code>null</code> if not found.
   */
  public Integer findCosmeticID(int itemID)
  {
    for(Map.Entry<Integer,int[]> entry : _cosmeticIDToItemIDs.entrySet())
    {
      for(int id : entry.getValue())
      {
        if (id==itemID)
        {
          return entry.getKey();
        }
      }
    }
    return null;
  }

  /**
   * Find the item IDs for the given cosmetic ID.
   * @param cosmeticID Cosmetic ID to search.
   * @return An array or item IDs, or <code>null</code>.
   */
  public int[] findItemIDs(int cosmeticID)
  {
    return _cosmeticIDToItemIDs.get(Integer.valueOf(cosmeticID));
  }
}
