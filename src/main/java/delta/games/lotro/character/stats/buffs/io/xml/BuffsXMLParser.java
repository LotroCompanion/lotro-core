package delta.games.lotro.character.stats.buffs.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;

/**
 * Read some buffs from XML documents.
 * @author DAM
 */
public class BuffsXMLParser
{
  /**
   * Load some buffs from a XML tag.
   * @param root Root tag.
   * @return the loaded data.
   */
  public static RawBuffStorage parseBuffs(Element root)
  {
    RawBuffStorage ret=new RawBuffStorage();
    if (root!=null)
    {
      List<Element> buffTags=DOMParsingTools.getChildTagsByName(root,BuffsXMLConstants.BUFF_TAG,true);
      for(Element buffTag : buffTags)
      {
        NamedNodeMap attrs=buffTag.getAttributes();
        String id=DOMParsingTools.getStringAttribute(attrs,BuffsXMLConstants.BUFF_ID_ATTR,"");
        int tier=DOMParsingTools.getIntAttribute(attrs,BuffsXMLConstants.BUFF_TIER_ATTR,1);
        ret.add(id,tier);
      }
    }
    return ret;
  }
}
