package delta.games.lotro.character.storage.stash.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.stash.ItemsStash;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.io.xml.ItemXMLConstants;
import delta.games.lotro.lore.items.io.xml.ItemXMLParser;

/**
 * Parser for stashs stored in XML.
 * @author DAM
 */
public class StashXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @param data Data to write to.
   * @return Parsed character or <code>null</code>.
   */
  public boolean parseXML(File source, ItemsStash data)
  {
    boolean ret=false;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseStash(root,data);
    }
    return ret;
  }

  private boolean parseStash(Element root, ItemsStash stash)
  {
    stash.clear();
    // Next ID
    NamedNodeMap attrs=root.getAttributes();
    int nextId=DOMParsingTools.getIntAttribute(attrs,StashXMLConstants.NEXT_ID_ATTR,-1);
    if (nextId>0)
    {
      stash.setNextId(nextId);
    }

    ItemXMLParser parser=new ItemXMLParser();
    List<Element> itemTags=DOMParsingTools.getChildTagsByName(root,ItemXMLConstants.ITEM_TAG,false);
    for(Element itemTag : itemTags)
    {
      Item item=parser.parseItem(itemTag);
      stash.registerItem(item);
    }
    return true;
  }
}
