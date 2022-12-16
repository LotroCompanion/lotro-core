package delta.games.lotro.character.storage.bags.io.xml;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.bags.BagsSetup;
import delta.games.lotro.character.storage.bags.SingleBagSetup;
import delta.games.lotro.common.status.io.xml.StatusMetadataIO;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.io.xml.ItemXMLConstants;
import delta.games.lotro.lore.items.io.xml.ItemXMLParser;

/**
 * Parser for the bags stored in XML.
 * @author DAM
 */
public class BagsXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(BagsXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed bags or <code>null</code>.
   */
  public BagsManager parseXML(File source)
  {
    BagsManager bags=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      bags=parseBags(root);
    }
    return bags;
  }

  private BagsManager parseBags(Element root)
  {
    BagsManager bags=new BagsManager();
    // Status
    StatusMetadataIO.parseStatusMetadata(root,bags.getStatusMetadata());
    // Setup
    BagsSetup setup=new BagsSetup();
    List<Element> setupTags=DOMParsingTools.getChildTagsByName(root,BagsXMLConstants.SETUP_TAG,false);
    int nbSetups=setupTags.size();
    boolean legacy=(nbSetups==0);
    if (!legacy)
    {
      for(Element setupTag : setupTags)
      {
        SingleBagSetup bagSetup=parseBagSetup(setupTag);
        setup.addBagSetup(bagSetup);
      }
    }
    // Slots
    List<Element> slotTags=DOMParsingTools.getChildTagsByName(root,BagsXMLConstants.SLOT_TAG,false);
    for(Element slotTag : slotTags)
    {
      parseBagSlot(bags,slotTag);
    }
    if (legacy)
    {
      int max=DOMParsingTools.getIntAttribute(root.getAttributes(),BagsXMLConstants.BAGS_MAX_ATTR,0);
      // Build a default setup (use the first available slots)
      SingleBagSetup bagSetup=new SingleBagSetup(1,max);
      List<Integer> indexes=bags.getIndexes();
      int nbItems=indexes.size();
      for(int i=0;i<nbItems;i++)
      {
        bagSetup.setItemIndexAt(i,indexes.get(i));
      }
      setup.addBagSetup(bagSetup);
    }
    bags.setBagsSetup(setup);
    return bags;
  }

  private void parseBagSlot(BagsManager bags, Element slotTag)
  {
    NamedNodeMap attrs=slotTag.getAttributes();
    int index=DOMParsingTools.getIntAttribute(attrs,BagsXMLConstants.SLOT_INDEX_ATTR,-1);
    if (index==-1)
    {
      // No index!
      LOGGER.warn("No index!");
      return;
    }
    int count=DOMParsingTools.getIntAttribute(attrs,BagsXMLConstants.SLOT_COUNT_ATTR,1);
    ItemXMLParser parser=new ItemXMLParser();
    Element itemTag=DOMParsingTools.getChildTagByName(slotTag,ItemXMLConstants.ITEM_TAG);
    if (itemTag!=null)
    {
      ItemInstance<? extends Item> item=parser.parseItemInstance(itemTag);
      if (item!=null)
      {
        CountedItem<ItemInstance<? extends Item>> countedItemInstance=new CountedItem<ItemInstance<? extends Item>>(item,count);
        bags.addBagItem(countedItemInstance,index);
      }
    }
  }

  private SingleBagSetup parseBagSetup(Element rootTag)
  {
    NamedNodeMap attrs=rootTag.getAttributes();
    int bagIndex=DOMParsingTools.getIntAttribute(attrs,BagsXMLConstants.SETUP_BAG_INDEX_ATTR,-1);
    if (bagIndex==-1)
    {
      // No index!
      LOGGER.warn("No index!");
      return null;
    }
    int bagSize=DOMParsingTools.getIntAttribute(attrs,BagsXMLConstants.SETUP_BAG_SIZE_ATTR,0);
    SingleBagSetup ret=new SingleBagSetup(bagIndex,bagSize);
    // Slots
    List<Element> slotSetupTags=DOMParsingTools.getChildTagsByName(rootTag,BagsXMLConstants.SLOT_SETUP_TAG);
    for(Element slotSetupTag : slotSetupTags)
    {
      NamedNodeMap slotAttrs=slotSetupTag.getAttributes();
      int slotIndex=DOMParsingTools.getIntAttribute(slotAttrs,BagsXMLConstants.SLOT_SETUP_SLOT_INDEX_ATTR,-1);
      int itemIndex=DOMParsingTools.getIntAttribute(slotAttrs,BagsXMLConstants.SLOT_SETUP_ITEM_INDEX_ATTR,-1);
      if ((slotIndex!=-1) && (itemIndex!=-1))
      {
        ret.setItemIndexAt(slotIndex,Integer.valueOf(itemIndex));
      }
    }
    // Width
    int bagWidth=DOMParsingTools.getIntAttribute(attrs,BagsXMLConstants.SETUP_BAG_WIDTH_ATTR,10);
    ret.setWidth(bagWidth);
    return ret;
  }
}
