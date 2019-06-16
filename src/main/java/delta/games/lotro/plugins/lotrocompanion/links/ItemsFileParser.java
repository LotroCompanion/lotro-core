package delta.games.lotro.plugins.lotrocompanion.links;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.plugins.LuaParser;
import delta.games.lotro.plugins.LuaUtils;
import delta.games.lotro.plugins.lotrocompanion.GearParser;

/**
 * Parser for the items file.
 * @author DAM
 */
public class ItemsFileParser
{
  private static final Logger LOGGER=Logger.getLogger(GearParser.class);

  /**
   * Parse/use data from the "Items" file of the LotroCompanion plugin.
   * @param f Input file.
   * @throws Exception If an error occurs.
   */
  public void doIt(File f) throws Exception
  {
    LuaParser parser=new LuaParser();
    Map<String,Object> data=parser.read(f);
    useData(data);
  }

  /**
   * Parse/use data from the "Items" file of the LotroCompanion plugin.
   * @param inputString Input string.
   */
  public void doIt(String inputString)
  {
    LuaParser parser=new LuaParser();
    @SuppressWarnings("unchecked")
    Map<String,Object> data=(Map<String,Object>)parser.read(inputString);
    useData(data);
  }

  @SuppressWarnings("unchecked")
  private void useData(Map<String,Object> data)
  {
    Double counter=(Double)data.get("counter");
    int nb=(counter!=null)?counter.intValue():100;
    for(int i=1;i<nb;i++)
    {
      String key=String.valueOf(i)+".0";
      Map<String,Object> itemData=(Map<String,Object>)data.get(key);
      if (itemData!=null)
      {
        decodeItem(itemData);
      }
    }
  }

  private ItemInstance<? extends Item> decodeItem(Map<String,Object> itemData)
  {
    ItemInstance<? extends Item> ret=null;
    @SuppressWarnings("unchecked")
    Map<String,Object> itemRawData=(Map<String,Object>)itemData.get("item");
    Double timestamp=(Double)itemData.get("timestamp");
    long itemTime=timestamp.longValue()*1000;
    System.out.println(new Date(itemTime));
    byte[] buffer=LuaUtils.loadBuffer(itemRawData,"rawData");
    ChatItemLinksDecoder decoder=new ChatItemLinksDecoder();
    try
    {
      ret=decoder.decodeBuffer(buffer);
      System.out.println(ret.dump());
    }
    catch(LinkDecodingException e)
    {
      LOGGER.error("Caught item decoding exception!",e);
    }
    return ret;
  }
}
