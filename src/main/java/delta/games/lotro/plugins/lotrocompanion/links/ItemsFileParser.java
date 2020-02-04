package delta.games.lotro.plugins.lotrocompanion.links;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.plugins.LuaParser;
import delta.games.lotro.plugins.LuaUtils;
import delta.games.lotro.plugins.PluginConstants;

/**
 * Parser for the items file.
 * @author DAM
 */
public class ItemsFileParser
{
  private static final Logger LOGGER=Logger.getLogger(ItemsFileParser.class);

  /**
   * Get the item instances from game for a toon.
   * @param toon Targeted toon.
   * @return A possibly empty but never <code>null</code> list of item instances.
   */
  public List<ItemInstance<? extends Item>> getItemsForToon(CharacterFile toon)
  {
    List<ItemInstance<? extends Item>> ret=new ArrayList<ItemInstance<? extends Item>>();
    String account=toon.getAccountName();
    String server=toon.getServerName();
    String character=toon.getName();
    File dataDir=PluginConstants.getCharacterDir(account,server,character);
    File dataFile=new File(dataDir,"LotroCompanionItems.plugindata");
    if (dataFile.exists())
    {
      ItemsFileParser parser=new ItemsFileParser();
      try
      {
        List<ItemInstance<? extends Item>> items=parser.doIt(dataFile);
        ret.addAll(items);
      }
      catch(Exception e)
      {
        LOGGER.warn("Could not parse items file: "+dataFile,e);
      }
    }
    else
    {
      LOGGER.warn("File "+dataFile+" does not exist!");
    }
    LOGGER.info("Got "+ret.size()+" items from file: "+dataFile);
    return ret;
  }

  /**
   * Parse/use data from the "Items" file of the LotroCompanion plugin.
   * @param f Input file.
   * @return the list of decoded item instances.
   * @throws Exception If an error occurs.
   */
  public List<ItemInstance<? extends Item>> doIt(File f) throws Exception
  {
    LuaParser parser=new LuaParser();
    Map<String,Object> data=parser.read(f);
    return useData(data);
  }

  /**
   * Parse/use data from the "Items" file of the LotroCompanion plugin.
   * @param inputString Input string.
   * @return the list of decoded item instances.
   */
  public List<ItemInstance<? extends Item>> doIt(String inputString)
  {
    LuaParser parser=new LuaParser();
    @SuppressWarnings("unchecked")
    Map<String,Object> data=(Map<String,Object>)parser.read(inputString);
    List<ItemInstance<? extends Item>> ret=useData(data);
    return ret;
  }

  @SuppressWarnings("unchecked")
  private List<ItemInstance<? extends Item>> useData(Map<String,Object> data)
  {
    List<ItemInstance<? extends Item>> ret=new ArrayList<ItemInstance<? extends Item>>();
    //Double counter=(Double)data.get("counter");
    int nb=100;
    for(int i=1;i<nb;i++)
    {
      String key=String.valueOf(i)+".0";
      Map<String,Object> itemData=(Map<String,Object>)data.get(key);
      if (itemData!=null)
      {
        ItemInstance<? extends Item> decodedItem=decodeItem(itemData);
        if (decodedItem!=null)
        {
          ret.add(decodedItem);
        }
      }
    }
    return ret;
  }

  private ItemInstance<? extends Item> decodeItem(Map<String,Object> itemData)
  {
    ItemInstance<? extends Item> ret=null;
    @SuppressWarnings("unchecked")
    Map<String,Object> itemRawData=(Map<String,Object>)itemData.get("item");
    byte[] buffer=LuaUtils.loadBuffer(itemRawData,"rawData");
    ChatItemLinksDecoder decoder=new ChatItemLinksDecoder();
    try
    {
      ret=decoder.decodeBuffer(buffer);
      if (ret!=null)
      {
        Double timestamp=(Double)itemData.get("timestamp");
        if (timestamp!=null)
        {
          long itemTime=timestamp.longValue()*1000;
          ret.setTime(Long.valueOf(itemTime));
        }
        else
        {
          ret.setTime(Long.valueOf(System.currentTimeMillis()));
        }
      }
      //System.out.println(ret.dump());
    }
    catch(LinkDecodingException e)
    {
      LOGGER.error("Caught item decoding exception!",e);
    }
    return ret;
  }
}
