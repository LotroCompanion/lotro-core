package delta.games.lotro.plugins.lotrocompanion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemPropertyNames;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.recipes.Recipe;
import delta.games.lotro.plugins.LuaParser;
import delta.games.lotro.plugins.PluginConstants;

/**
 * Parser for the gear as found in LotroCompanion plugin data.
 * @author DAM
 */
public class GearParser
{
  /**
   * Parse/use data from the "Recipes" file of the LotroCompanion plugin.
   * @param f Input file.
   * @return A possibly empty list of recipes.
   * @throws Exception If an error occurs.
   */
  public void doIt(File f) throws Exception
  {
    LuaParser parser=new LuaParser();
    Map<String,Object> data=parser.read(f);
    useData(data);
  }

  private static EQUIMENT_SLOT[] SLOTS={
    EQUIMENT_SLOT.HEAD, EQUIMENT_SLOT.BREAST, EQUIMENT_SLOT.LEGS, EQUIMENT_SLOT.HANDS,
    EQUIMENT_SLOT.FEET, EQUIMENT_SLOT.SHOULDER, EQUIMENT_SLOT.BACK, EQUIMENT_SLOT.LEFT_WRIST,
    EQUIMENT_SLOT.RIGHT_WRIST, EQUIMENT_SLOT.NECK, EQUIMENT_SLOT.LEFT_FINGER, EQUIMENT_SLOT.RIGHT_FINGER,
    EQUIMENT_SLOT.LEFT_EAR, EQUIMENT_SLOT.RIGHT_EAR, EQUIMENT_SLOT.POCKET,
    EQUIMENT_SLOT.MAIN_MELEE, EQUIMENT_SLOT.OTHER_MELEE, EQUIMENT_SLOT.RANGED, EQUIMENT_SLOT.TOOL,
    EQUIMENT_SLOT.CLASS_ITEM
  };

  @SuppressWarnings("unchecked")
  private void useData(Map<String,Object> data)
  {
    List<Recipe> ret=new ArrayList<Recipe>();
    Map<String,Object> gearMap=(Map<String,Object>)data.get("gear");
    if (gearMap!=null)
    {
      Set<String> slotIndexKeys=gearMap.keySet();
      for(String slotIndexKey : slotIndexKeys)
      {
        Map<String,Object> slotMap=(Map<String,Object>)gearMap.get(slotIndexKey);
        if (slotMap!=null)
        {
          int slotIndex=(int)NumericTools.parseDouble(slotIndexKey,0);
          if (slotIndex!=0)
          {
            parseSlot(slotIndex,slotMap);
          }
        }
      }
    }
  }

  private void parseSlot(int slotIndex, Map<String,Object> slotMap)
  {
    Boolean used=(Boolean)slotMap.get("Item");
    if ((used!=null) && (used.booleanValue()))
    {
      Double iconId=(Double)slotMap.get("IImgID");
      Double backgroundIconId=(Double)slotMap.get("BImgID");
      String name=(String)slotMap.get("Name");
      EQUIMENT_SLOT slot=SLOTS[slotIndex-1];
      EquipmentLocation location=slot.getLocation();
      Item item=findItem(name,String.valueOf(iconId.intValue()),String.valueOf(backgroundIconId.intValue()),location);
      System.out.println(item);
    }
  }

  private Item findItem(String name, String iconId, String backgroundIconId, EquipmentLocation location)
  {
    List<Item> ret=new ArrayList<Item>();
    List<Item> items=ItemsManager.getInstance().getAllItems();
    for(Item item : items)
    {
      if (name.equals(item.getName()))
      {
        String itemIconId=item.getProperty(ItemPropertyNames.ICON_ID);
        String itemBackgroundIconId=item.getProperty(ItemPropertyNames.BACKGROUND_ICON_ID);
        if ((itemIconId.equals(iconId)) && (itemBackgroundIconId.equals(backgroundIconId)))
        {
          ret.add(item);
        }
      }
    }
    if (ret.size()>1)
    {
      System.out.println("Ambiguity on " + name + ": " + ret.size());
    }
    if (ret.size()>0)
    {
      return ret.get(0);
    }
    return null;
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    String account="glorfindel666";
    String server="Landroval";
    List<String> characters=PluginConstants.getCharacters(account,server,false);
    for(String character : characters)
    {
      try
      {
        File dataFile=PluginConstants.getCharacterDir("glorfindel666","Landroval",character);
        File recipesFile=new File(dataFile,"LotroCompanionData.plugindata");
        if (recipesFile.exists())
        {
          System.out.println("Doing: " + character);
          GearParser parser=new GearParser();
          parser.doIt(recipesFile);
        }
        else
        {
          System.out.println("No recipes for: " + character);
        }
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
  }
}
