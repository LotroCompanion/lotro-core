package delta.games.lotro.character.status.summary.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.summary.AchievementsSummary;

/**
 * Parser for the storage summaries stored in XML.
 * @author DAM
 */
public class AchievementsSummaryXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed summary or <code>null</code>.
   */
  public AchievementsSummary parseXML(File source)
  {
    AchievementsSummary ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseAchievementsSummary(root);
    }
    return ret;
  }

  private AchievementsSummary parseAchievementsSummary(Element root)
  {
    AchievementsSummary ret=new AchievementsSummary();
    NamedNodeMap attrs=root.getAttributes();
    // Deeds
    int deeds=DOMParsingTools.getIntAttribute(attrs,AchievementsSummaryXMLConstants.DEEDS_ATTR,0);
    ret.setDeedsCount(deeds);
    // Quests
    int quests=DOMParsingTools.getIntAttribute(attrs,AchievementsSummaryXMLConstants.QUESTS_ATTR,0);
    ret.setQuestsCount(quests);
    // Titles
    int titles=DOMParsingTools.getIntAttribute(attrs,AchievementsSummaryXMLConstants.TITLES_ATTR,0);
    ret.setTitlesCount(titles);
    return ret;
  }
}
