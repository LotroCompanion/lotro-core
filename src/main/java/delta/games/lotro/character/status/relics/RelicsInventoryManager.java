package delta.games.lotro.character.status.relics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.status.relics.comparators.RelicInventoryEntrySortUtils;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;

/**
 * Storage for all relics entries for a single character.
 * @author DAM
 */
public class RelicsInventoryManager
{
  private Map<Integer,RelicsInventoryEntry> _relicEntries;

  /**
   * Constructor.
   */
  public RelicsInventoryManager()
  {
    _relicEntries=new HashMap<Integer,RelicsInventoryEntry>(); 
  }

  /**
   * Initialize from a relics inventory.
   * @param relicsInventory Relics inventory.
   */
  public void init(RelicsInventory relicsInventory)
  {
    _relicEntries.clear();
    RelicsManager relicsMgr=RelicsManager.getInstance();
    for(Integer relicId : relicsInventory.getRelicIdentifiers())
    {
      Relic relic=relicsMgr.getById(relicId.intValue());
      if (relic==null)
      {
        continue;
      }
      int count=relicsInventory.getRelicCount(relicId.intValue());
      RelicsInventoryEntry entry=new RelicsInventoryEntry(relic,count);
      _relicEntries.put(relicId,entry);
    }
  }

  /**
   * Get the managed relic entries.
   * @return a list of relic entries.
   */
  public List<RelicsInventoryEntry> getRelicEntries()
  {
    List<RelicsInventoryEntry> ret=new ArrayList<RelicsInventoryEntry>(_relicEntries.values());
    RelicInventoryEntrySortUtils.sortByName(ret);
    return ret;
  }

  /**
   * Get the entry for a relic.
   * @param relicId Relic identifier.
   * @return A relic inventory entry or <code>null</code> if not found.
   */
  public RelicsInventoryEntry getRelic(int relicId)
  {
    return _relicEntries.get(Integer.valueOf(relicId));
  }
}
