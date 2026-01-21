package delta.games.lotro.character.storage.cosmetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.storage.StoredItem;
import delta.games.lotro.lore.items.cosmetics.ItemCosmetics;
import delta.games.lotro.lore.items.cosmetics.ItemCosmeticsManager;

/**
 * Finds items with similar look/cosmetics.
 * @author DAM
 */
public class SameCosmeticsFinder
{
  /**
   * Find groups of items with same cosmetics.
   * @param items Item to look.
   * @return A possibly empty but never <code>null</code> list of groups.
   */
  public List<CosmeticItemsGroup> findGroups(List<StoredItem> items)
  {
    Map<Integer,CosmeticItemsGroup> groups=new HashMap<Integer,CosmeticItemsGroup>();
    groups.clear();
    ItemCosmetics cosmetics=ItemCosmeticsManager.getInstance().getData();
    for(StoredItem item : items)
    {
      Integer cosmeticID=cosmetics.findCosmeticID(item.getItem().getIdentifier());
      if (cosmeticID!=null)
      {
        CosmeticItemsGroup group=groups.get(cosmeticID);
        if (group==null)
        {
          group=new CosmeticItemsGroup(cosmeticID.intValue());
          groups.put(cosmeticID,group);
        }
        group.addItem(item);
      }
    }
    List<CosmeticItemsGroup> ret=new ArrayList<CosmeticItemsGroup>();
    List<Integer> cosmeticIDs=new ArrayList<Integer>(groups.keySet());
    Collections.sort(cosmeticIDs);
    int groupID=1;
    for(Integer cosmeticID : cosmeticIDs)
    {
      CosmeticItemsGroup group=groups.get(cosmeticID);
      if (group.getItems().size()>1)
      {
        ret.add(group);
        group.setGroupID(groupID);
        groupID++;
      }
    }
    return ret;
  }
}
