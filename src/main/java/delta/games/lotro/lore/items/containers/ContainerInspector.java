package delta.games.lotro.lore.items.containers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.lore.items.Container;
import delta.games.lotro.lore.items.ContainersManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsContainer;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsContainer;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;

/**
 * Containers inspector.
 * @author DAM
 */
public class ContainerInspector
{
  /**
   * Get the possible contents of a container. 
   * @param sourceItem Source item.
   * @return A possibly empty but never <code>null</code> list of items.
   */
  public static List<Item> getContainerContents(Item sourceItem)
  {
    List<Item> ret=new ArrayList<Item>();
    Set<Integer> itemIds=new HashSet<Integer>();
    ContainersManager containersMgr=ContainersManager.getInstance();
    Container container=containersMgr.getContainerById(sourceItem.getIdentifier());
    if (container instanceof ItemsContainer)
    {
      ItemsContainer itemsContainer=(ItemsContainer)container;
      itemIds.addAll(itemsContainer.getItemIds());
    }
    for(Integer itemId : itemIds)
    {
      Item item=ItemsManager.getInstance().getItem(itemId.intValue());
      if (item!=null)
      {
        ret.add(item);
      }
    }
    return ret;
  }

  /**
   * Get the possible contents of a container. 
   * @param sourceItem Source item.
   * @return A possibly empty but never <code>null</code> list of relics.
   */
  public static List<Relic> getContainerRelics(Item sourceItem)
  {
    List<Relic> ret=new ArrayList<Relic>();
    Set<Integer> relicIds=new HashSet<Integer>();
    ContainersManager containersMgr=ContainersManager.getInstance();
    Container container=containersMgr.getContainerById(sourceItem.getIdentifier());
    if (container instanceof RelicsContainer)
    {
      RelicsContainer relicsContainer=(RelicsContainer)container;
      relicIds.addAll(relicsContainer.getRelicds());
    }
    for(Integer relicId : relicIds)
    {
      Relic relic=RelicsManager.getInstance().getById(relicId.intValue());
      if (relic!=null)
      {
        ret.add(relic);
      }
    }
    return ret;
  }
}
