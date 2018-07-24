package delta.games.lotro.plugins.lotrocompanion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.plugins.LuaParser;
import delta.games.lotro.plugins.PluginConstants;

/**
 * Parser for the gear as found in LotroCompanion plugin data.
 * @author DAM
 */
public class GearParser
{
  private static final Logger LOGGER=Logger.getLogger(GearParser.class);

  /**
   * Parse/use data from the "Gear" file of the LotroCompanion plugin.
   * @param f Input file.
   * @throws Exception If an error occurs.
   */
  public void doIt(File f) throws Exception
  {
    LuaParser parser=new LuaParser();
    Map<String,Object> data=parser.read(f);
    useData(data);
  }

  private static final EQUIMENT_SLOT[] SLOTS={
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
      Item item=findItem(name,iconId.intValue()+"-"+backgroundIconId.intValue());
      System.out.println(location+": "+item);
    }
  }

  private Item findItem(String name, String icon)
  {
    List<Item> ret=new ArrayList<Item>();
    List<Item> retWithRightIcons=new ArrayList<Item>();
    List<Item> items=ItemsManager.getInstance().getAllItems();
    for(Item item : items)
    {
      String itemIcon=item.getIcon();
      if (itemIcon.equals(icon))
      {
        retWithRightIcons.add(item);
        if (name.equals(item.getName()))
        {
          ret.add(item);
        }
      }
    }
    if (ret.size()==0)
    {
      ret.addAll(retWithRightIcons);
    }
    if (ret.size()>1)
    {
      System.err.println("Ambiguity on " + name + ": " + ret.size());
      for(Item item : ret)
      {
        System.err.println("#" + item.dump());
      }
    }
    if (ret.size()>0)
    {
      return ret.get(0);
    }
    System.err.println("Not found:" + name);
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
      File dataDir=PluginConstants.getCharacterDir(account,server,character);
      File dataFile=new File(dataDir,"LotroCompanionData.plugindata");
      if (dataFile.exists())
      {
        try
        {
          System.out.println("Doing: " + character);
          GearParser parser=new GearParser();
          parser.doIt(dataFile);
        }
        catch(Exception e)
        {
          LOGGER.error("Error when loading gear from file "+dataFile, e);
        }
      }
      else
      {
        System.out.println("No data for: " + character);
      }
    }
  }
}
