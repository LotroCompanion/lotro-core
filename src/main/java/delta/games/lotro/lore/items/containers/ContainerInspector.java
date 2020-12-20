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

/**
 * Containers inspector.
 * @author DAM
 */
public class ContainerInspector
{
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
}
