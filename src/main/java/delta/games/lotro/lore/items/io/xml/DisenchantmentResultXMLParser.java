package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.treasure.LootsManager;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.DisenchantmentResult;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Parser for disenchantment results stored in XML.
 * @author DAM
 */
public class DisenchantmentResultXMLParser
{
  private LootsManager _lootsMgr;

  /**
   * Constructor.
   */
  public DisenchantmentResultXMLParser()
  {
    _lootsMgr=LootsManager.getInstance();
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed disenchantment results or <code>null</code>.
   */
  public List<DisenchantmentResult> parseXML(File source)
  {
    List<DisenchantmentResult> ret=new ArrayList<DisenchantmentResult>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> tags=DOMParsingTools.getChildTagsByName(root,DisenchantmentResultXMLConstants.DISENCHANTMENT_TAG);
      for(Element tag : tags)
      {
        DisenchantmentResult disenchantmentResult=parseDisenchantmentResult(tag);
        ret.add(disenchantmentResult);
      }
    }
    return ret;
  }

  private DisenchantmentResult parseDisenchantmentResult(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,DisenchantmentResultXMLConstants.SOURCE_ITEM_ID_ATTR,0);
    Item sourceItem=ItemsManager.getInstance().getItem(id);
    DisenchantmentResult ret=new DisenchantmentResult(sourceItem);

    // Counted items
    int itemId=DOMParsingTools.getIntAttribute(attrs,DisenchantmentResultXMLConstants.RESULT_ITEM_ID_ATTR,0);
    if (itemId!=0)
    {
      Item item=ItemsManager.getInstance().getItem(itemId);
      int quantity=DOMParsingTools.getIntAttribute(attrs,DisenchantmentResultXMLConstants.RESULT_QUANTITY_ATTR,0);
      CountedItem<Item> countedItem=new CountedItem<Item>(item,quantity);
      ret.setCountedItem(countedItem);
    }
    // Trophy list
    int trophyListId=DOMParsingTools.getIntAttribute(attrs,DisenchantmentResultXMLConstants.TROPHY_LIST_ID_ATTR,0);
    if (trophyListId!=0)
    {
      TrophyList trophyList=(TrophyList)_lootsMgr.getTables().getItem(trophyListId);
      ret.setTrophyList(trophyList);
    }
    return ret;
  }
}
