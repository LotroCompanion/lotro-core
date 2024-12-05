package delta.games.lotro.misc.migration;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.storage.bags.io.BagsIo;
import delta.games.lotro.character.storage.carryAlls.CarryAllInstance;
import delta.games.lotro.character.storage.carryAlls.CarryAllsManager;
import delta.games.lotro.character.storage.carryAlls.CarryAllsUtils;
import delta.games.lotro.character.storage.carryAlls.io.xml.CarryAllInstanceXMLConstants;
import delta.games.lotro.character.storage.carryAlls.io.xml.CarryAllInstanceXMLParser;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.carryalls.CarryAll;
import delta.games.lotro.lore.items.io.xml.ItemXMLConstants;

/**
 * Migration of carry-all data, from inventory.xml to dedicated carry-all instance files.
 * @author DAM
 */
public class CarryAllsMigration
{
  private static final Logger LOGGER=LoggerFactory.getLogger(CarryAllsMigration.class);

  /**
   * Perform migration.
   */
  public void doIt()
  {
    CharactersManager charactersMgr=CharactersManager.getInstance();
    List<CharacterFile> toons=charactersMgr.getAllToons();
    for(CharacterFile toon : toons)
    {
      try
      {
        handleToon(toon);
      }
      catch(Exception e)
      {
        LOGGER.warn("Error during carry-all migration for character: {}",toon);
      }
    }
  }

  private void handleToon(CharacterFile toon)
  {
    File bagsFile=BagsIo.getBagsFile(toon);
    if (!bagsFile.exists())
    {
      return;
    }
    Element root=DOMParsingTools.parse(bagsFile);
    if (root==null)
    {
      return;
    }
    CarryAllsManager carryAllsMgr=CarryAllsUtils.buildCarryAllManager(toon);
    if (carryAllsMgr==null)
    {
      return;
    }
    List<Element> itemTags=DOMParsingTools.getChildTagsByName(root,ItemXMLConstants.ITEM_TAG);
    for(Element itemTag : itemTags)
    {
      Element carryAllTag=DOMParsingTools.getChildTagByName(itemTag,CarryAllInstanceXMLConstants.CARRY_ALL);
      if (carryAllTag==null)
      {
        continue;
      }
      String instanceIdStr=DOMParsingTools.getStringAttribute(itemTag.getAttributes(),ItemXMLConstants.ITEM_INSTANCE_ID_ATTR,null);
      if (instanceIdStr==null)
      {
        continue;
      }
      InternalGameId instanceId=InternalGameId.fromString(instanceIdStr);
      boolean exists=carryAllsMgr.hasCarryAll(instanceId);
      if (!exists)
      {
        CarryAllInstance carryAll=CarryAllInstanceXMLParser.read(carryAllTag);
        carryAll.setId(instanceId);
        int itemID=DOMParsingTools.getIntAttribute(itemTag.getAttributes(),ItemXMLConstants.ITEM_KEY_ATTR,-1);
        Item item=ItemsManager.getInstance().getItem(itemID);
        carryAll.setReference((CarryAll)item);
        carryAllsMgr.updateCarryAll(carryAll);
      }
    }
  }

  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new CarryAllsMigration().doIt();
  }
}
