package delta.games.lotro.lore.items.legendary.passives.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.legendary.passives.PassivesGroup;

/**
 * Parser for passives usage stored in XML.
 * @author DAM
 */
public class PassivesGroupsXMLParser
{
  /**
   * Read passives usage from a file.
   * @param from Input file.
   * @return a list of all loaded groups.
   */
  public static List<PassivesGroup> parsePassivesUsageFile(File from)
  {
    Element root=DOMParsingTools.parse(from);
    List<PassivesGroup> ret=new ArrayList<PassivesGroup>();
    List<Element> groupTags=DOMParsingTools.getChildTagsByName(root,PassivesGroupsXMLConstants.PASSIVES_GROUP_TAG);
    for(Element groupTag : groupTags)
    {
      PassivesGroup group=new PassivesGroup();
      // Passives
      List<Element> passiveTags=DOMParsingTools.getChildTagsByName(groupTag,PassivesGroupsXMLConstants.PASSIVE_TAG);
      for(Element passiveTag : passiveTags)
      {
        int passiveId=DOMParsingTools.getIntAttribute(passiveTag.getAttributes(),PassivesGroupsXMLConstants.PASSIVE_ID_ATTR,0);
        if (passiveId!=0)
        {
          group.addPassive(passiveId);
        }
      }
      // Item identifiers
      List<Element> itemTags=DOMParsingTools.getChildTagsByName(groupTag,PassivesGroupsXMLConstants.ITEM_TAG);
      for(Element itemTag : itemTags)
      {
        int itemId=DOMParsingTools.getIntAttribute(itemTag.getAttributes(),PassivesGroupsXMLConstants.ITEM_ID_ATTR,0);
        if (itemId!=0)
        {
          group.addItem(itemId);
        }
      }
      ret.add(group);
    }
    return ret;
  }
}
