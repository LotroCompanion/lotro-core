package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Find items using their name/icons.
 * @author DAM
 */
public class ItemsFinder
{
  private static final Logger LOGGER=Logger.getLogger(ItemsFinder.class);

  private HashMap<String,List<Item>> _icons;

  /**
   * Constructor.
   */
  public ItemsFinder()
  {
    _icons=loadItemsMap();
  }

  /**
   * Build a proxy for an item.
   * @param itemName Item name.
   * @param iconId Item icon id.
   * @return A proxy.
   */
  public ItemProxy buildProxy(String itemName, int iconId)
  {
    Item item=resolve(itemName,iconId);
    String icon=String.valueOf(iconId);
    return buildProxy(item,itemName,icon);
  }

  /**
   * Build a proxy for an item.
   * @param itemName Item name.
   * @param iconId Icon id of the item.
   * @param backgroundIconId Background icon id of the item.
   * @return A proxy.
   */
  public ItemProxy buildProxy(String itemName, int iconId, int backgroundIconId)
  {
    Item item=resolve(itemName,iconId,backgroundIconId);
    String icon=iconId+"-"+backgroundIconId;
    return buildProxy(item,itemName,icon);
  }

  private ItemProxy buildProxy(Item item, String itemName, String icon)
  {
    ItemProxy proxy=new ItemProxy();
    if (item!=null)
    {
      proxy.setItem(item);
    }
    else
    {
      proxy.setName(itemName);
      proxy.setIcon(icon);
    }
    return proxy;
  }

  private Item resolve(String name, int iconId)
  {
    String icon=String.valueOf(iconId);
    return resolve(name,icon);
  }

  private Item resolve(String name, int iconId, int backgroundIconId)
  {
    String icon=iconId+"-"+backgroundIconId;
    return resolve(name,icon);
  }

  private Item resolve(String name, String iconKey)
  {
    List<Item> ret=new ArrayList<Item>();
    List<Item> itemsWithRightIcon=getItems(iconKey);
    if (itemsWithRightIcon!=null)
    {
      for(Item item : itemsWithRightIcon)
      {
        if (name.equals(item.getName()))
        {
          ret.add(item);
        }
      }
      if (ret.size()==0)
      {
        ret.addAll(itemsWithRightIcon);
      }
    }

    if (ret.size()>1)
    {
      LOGGER.warn("Ambiguity on " + name + ": " + ret.size());
      for(Item item : ret)
      {
        String itemName=item.getName();
        String subCategory=item.getSubCategory();
        LOGGER.warn("\t#" + itemName + " - " + subCategory);
      }
    }
    if (ret.size()>0)
    {
      return ret.get(0);
    }
    LOGGER.warn("Not found:" + name);
    return null;
  }

  /**
   * Get an item using a 'key' (name, lorebook key, icon path).
   * @param key Key to use.
   * @return An item or <code>null</code> if not found.
   */
  private List<Item> getItems(String key)
  {
    List<Item> items=_icons.get(key);
    return items;
  }

  /**
   * Load map (keys/names)->list of item ids
   * @return a map.
   */
  private HashMap<String,List<Item>> loadItemsMap()
  {
    HashMap<String,List<Item>> idStr2Id=new HashMap<String,List<Item>>(); 
    List<Item> items=ItemsManager.getInstance().getAllItems();
    for(Item item : items)
    {
      String icon=item.getIcon();
      registerMapping(idStr2Id,icon,item);
      String mainIconId=icon.substring(0,icon.indexOf('-'));
      registerMapping(idStr2Id,mainIconId,item);
    }
    System.out.println(idStr2Id.size());
    return idStr2Id;
  }

  private void registerMapping(HashMap<String,List<Item>> map, String key, Item item)
  {
    if (key!=null)
    {
      List<Item> ids=map.get(key);
      if (ids==null)
      {
        ids=new ArrayList<Item>();
        map.put(key,ids);
      }
      if (!ids.contains(item))
      {
        ids.add(item);
      }
    }
  }
}
