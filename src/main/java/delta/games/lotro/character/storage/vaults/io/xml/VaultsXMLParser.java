package delta.games.lotro.character.storage.vaults.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.vaults.Chest;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.io.xml.ItemXMLConstants;
import delta.games.lotro.lore.items.io.xml.ItemXMLParser;

/**
 * Parser for the vaults stored in XML.
 * @author DAM
 */
public class VaultsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed bags or <code>null</code>.
   */
  public Vault parseXML(File source)
  {
    Vault status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseVault(root);
    }
    return status;
  }

  private Vault parseVault(Element root)
  {
    Vault vault=new Vault();
    NamedNodeMap attrs=root.getAttributes();
    // Used
    int used=DOMParsingTools.getIntAttribute(attrs,VaultsXMLConstants.VAULT_USED_ATTR,0);
    vault.setUsed(used);
    // Max
    int max=DOMParsingTools.getIntAttribute(attrs,VaultsXMLConstants.VAULT_MAX_ATTR,0);
    vault.setCapacity(max);
    // Chests
    List<Element> chestTags=DOMParsingTools.getChildTagsByName(root,VaultsXMLConstants.CHEST_TAG,false);
    for(Element chestTag : chestTags)
    {
      Chest chest=parseChest(chestTag);
      vault.addChest(chest);
    }
    return vault;
  }

  private Chest parseChest(Element chestTag)
  {
    NamedNodeMap chestAttrs=chestTag.getAttributes();
    int chestId=DOMParsingTools.getIntAttribute(chestAttrs,VaultsXMLConstants.CHEST_ID_ATTR,-1);
    Chest chest=new Chest(chestId);
    String chestName=DOMParsingTools.getStringAttribute(chestAttrs,VaultsXMLConstants.CHEST_NAME_ATTR,"");
    chest.setName(chestName);
    List<Element> slotTags=DOMParsingTools.getChildTagsByName(chestTag,VaultsXMLConstants.SLOT_TAG,false);
    for(Element slotTag : slotTags)
    {
      parseChestSlot(chest,slotTag);
    }
    return chest;
  }

  private void parseChestSlot(Chest chest, Element slotTag)
  {
    NamedNodeMap attrs=slotTag.getAttributes();
    int count=DOMParsingTools.getIntAttribute(attrs,VaultsXMLConstants.SLOT_COUNT_ATTR,1);
    ItemXMLParser parser=new ItemXMLParser();
    Element itemTag=DOMParsingTools.getChildTagByName(slotTag,ItemXMLConstants.ITEM_TAG);
    if (itemTag!=null)
    {
      ItemInstance<? extends Item> item=parser.parseItemInstance(itemTag);
      if (item!=null)
      {
        CountedItem<ItemInstance<? extends Item>> countedItemInstance=new CountedItem<ItemInstance<? extends Item>>(item,count);
        chest.addItem(countedItemInstance);
      }
    }
  }
}
