package delta.games.lotro.character.status.relics.io.xml;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.relics.RelicsInventory;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;

/**
 * Parser for the relics inventories stored in XML.
 * @author DAM
 */
public class RelicsInventoryXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(RelicsInventoryXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed inventory or <code>null</code>.
   */
  public RelicsInventory parseXML(File source)
  {
    RelicsInventory stats=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      stats=parseInventory(root);
    }
    return stats;
  }

  private RelicsInventory parseInventory(Element root)
  {
    RelicsInventory ret=new RelicsInventory();
    List<Element> relicTags=DOMParsingTools.getChildTagsByName(root,RelicsInventoryXMLConstants.RELIC_TAG,false);
    for(Element relicTag : relicTags)
    {
      parseRelic(ret,relicTag);
    }
    return ret;
  }

  private void parseRelic(RelicsInventory inventory, Element relicTag)
  {
    NamedNodeMap attrs=relicTag.getAttributes();
    int relicId=DOMParsingTools.getIntAttribute(attrs,RelicsInventoryXMLConstants.RELIC_ID_ATTR,0);
    RelicsManager relicsMgr=RelicsManager.getInstance();
    Relic relic=relicsMgr.getById(relicId);
    if (relic==null)
    {
      LOGGER.warn("Relic not found: {}",Integer.valueOf(relicId));
      return;
    }
    int relicCount=DOMParsingTools.getIntAttribute(attrs,RelicsInventoryXMLConstants.RELIC_COUNT_ATTR,0);
    inventory.setRelicCount(relicId,relicCount);
  }
}
