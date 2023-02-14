package delta.games.lotro.lore.items.paper.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.PaperItemCategory;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.paper.PaperItem;

/**
 * Parser for paper items stored in XML.
 * @author DAM
 */
public class PaperItemsXMLParser
{
  private LotroEnum<PaperItemCategory> _category;

  /**
   * Constructor.
   */
  public PaperItemsXMLParser()
  {
    LotroEnumsRegistry registry=LotroEnumsRegistry.getInstance();
    _category=registry.get(PaperItemCategory.class);
  }

  /**
   * Parse paper items from an XML file.
   * @param source Source file.
   * @return List of parsed paper items.
   */
  public List<PaperItem> parsepaperItemsFile(File source)
  {
    List<PaperItem> paperItems=new ArrayList<PaperItem>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> paperItemTags=DOMParsingTools.getChildTagsByName(root,PaperItemsXMLConstants.PAPER_ITEM_TAG);
      for(Element paperItemTag:paperItemTags)
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
  private PaperItem parsePaperItem(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_IDENTIFIER_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(id);
    PaperItem ret=new PaperItem(item);
    // Category
    int categoryCode=DOMParsingTools.getIntAttribute(attrs,PaperItemsXMLConstants.PAPER_ITEM_CATEGORY_ATTR,0);
    PaperItemCategory category=_category.getEntry(categoryCode);
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
