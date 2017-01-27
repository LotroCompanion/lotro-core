package delta.games.lotro.character.stats.buffs.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.buffs.BuffInstance;
import delta.games.lotro.character.stats.buffs.BuffRegistry;
import delta.games.lotro.character.stats.buffs.BuffsManager;

/**
 * Read some buffs from XML documents.
 * @author DAM
 */
public class BuffsXMLParser
{
  /**
   * Load some buffs from a XML tag.
   * @param root Root tag.
   * @param buffs Buffs storage.
   */
  public static void parseBuffs(Element root, BuffsManager buffs)
  {
    buffs.clear();
    if (root!=null)
    {
      List<Element> buffTags=DOMParsingTools.getChildTagsByName(root,BuffsXMLConstants.BUFF_TAG,true);
      BuffRegistry registry=BuffRegistry.getInstance();
      for(Element buffTag : buffTags)
      {
        NamedNodeMap attrs=buffTag.getAttributes();
        String id=DOMParsingTools.getStringAttribute(attrs,BuffsXMLConstants.BUFF_ID_ATTR,"");
        int tier=DOMParsingTools.getIntAttribute(attrs,BuffsXMLConstants.BUFF_TIER_ATTR,-1);
        BuffInstance buffInstance=registry.newBuffInstance(id);
        if (buffInstance!=null)
        {
          if (tier!=-1)
          {
            buffInstance.setTier(Integer.valueOf(tier));
          }
          buffs.addBuff(buffInstance);
        }
      }
    }
  }
}
