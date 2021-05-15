package delta.games.lotro.lore.items.paper.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.paper.PaperItem;

/**
 * Parser for paper items stored in XML.
 * @author DAM
 */
public class PaperItemsXMLParser
{
  /**
   * Parse paper items from an XML file.
   * @param source Source file.
   * @return List of parsed paper items.
   */
  public static List<PaperItem> parsepaperItemsFile(File source)
  {
    List<PaperItem> paperItems=new ArrayList<PaperItem>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> paperItemTags=DOMParsingTools.getChildTagsByName(root,PaperItemsXMLConstants.PAPER_ITEM_TAG);
      for(Element paperItemTag : paperItemTags)
      {
        PaperItem paperItem=parsePaperItem(paperItemTag);
        paperItems.add(paperItem);
      }
    }
    return paperItems;
  }

  /**
   * Build a paper item from an XML tag.
   * @param root Root XML tag.
   * @return A paper item.
   */
  private static PaperItem parsePaperItem(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_IDENTIFIER_ATTR,0);
    PaperItem ret=new PaperItem(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_NAME_ATTR,"");
    ret.setName(name);
    // Item class
    String itemClass=DOMParsingTools.getStringAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_CLASS_ATTR,"");
    ret.setItemClass(itemClass);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_CATEGORY_ATTR,"");
    ret.setCategory(category);
    // Shared
    boolean shared=DOMParsingTools.getBooleanAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_SHARED_ATTR,false);
    ret.setShared(shared);
    // Free
    boolean free=DOMParsingTools.getBooleanAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_FREE_ATTR,false);
    ret.setFree(free);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_ICON_ID_ATTR,0);
    if (iconId!=0)
    {
      ret.setIconId(Integer.valueOf(iconId));
    }
    // Cap
    int cap=DOMParsingTools.getIntAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_CAP_ATTR,0);
    if (cap!=0)
    {
      ret.setCap(Integer.valueOf(cap));
    }
    // Old
    boolean old=DOMParsingTools.getBooleanAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_OLD_ATTR,false);
    ret.setOld(old);

    return ret;
  }
}
