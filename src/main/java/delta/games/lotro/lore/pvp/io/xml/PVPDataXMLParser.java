package delta.games.lotro.lore.pvp.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.pvp.Rank;
import delta.games.lotro.lore.pvp.RankScale;
import delta.games.lotro.lore.pvp.RankScaleEntry;

/**
 * Parser for the PVP data stored in XML.
 * @author DAM
 */
public class PVPDataXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public List<RankScale> parseXML(File source)
  {
    List<RankScale> ret=new ArrayList<RankScale>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> scaleTags=DOMParsingTools.getChildTagsByName(root,PVPDataXMLConstants.RANK_SCALE_TAG);
      for(Element scaleTag : scaleTags)
      {
        RankScale rankScale=parseRankScale(scaleTag);
        ret.add(rankScale);
      }
    }
    return ret;
  }

  private RankScale parseRankScale(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    String key=DOMParsingTools.getStringAttribute(attrs,PVPDataXMLConstants.KEY_ATTR,"");
    RankScale ret=new RankScale(key);
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(root,PVPDataXMLConstants.RANK_SCALE_ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap entryAttrs=entryTag.getAttributes();
      // Code
      int code=DOMParsingTools.getIntAttribute(entryAttrs,PVPDataXMLConstants.CODE_ATTR,0);
      // Name
      String name=DOMParsingTools.getStringAttribute(entryAttrs,PVPDataXMLConstants.LABEL_ATTR,"");
      Rank rank=new Rank(code,name);
      // Min value
      int minValue=DOMParsingTools.getIntAttribute(entryAttrs,PVPDataXMLConstants.MIN_ATTR,0);
      RankScaleEntry entry=new RankScaleEntry(minValue,rank);
      ret.addEntry(entry);
    }
    return ret;
  }
}
