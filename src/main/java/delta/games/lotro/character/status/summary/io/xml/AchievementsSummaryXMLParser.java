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
    Integer deeds=DOMParsingTools.getIntegerAttribute(attrs,AchievementsSummaryXMLConstants.DEEDS_ATTR,null);
    ret.setDeedsCount(deeds);
    // Quests
    Integer quests=DOMParsingTools.getIntegerAttribute(attrs,AchievementsSummaryXMLConstants.QUESTS_ATTR,null);
    ret.setQuestsCount(quests);
    // Titles
    Integer titles=DOMParsingTools.getIntegerAttribute(attrs,AchievementsSummaryXMLConstants.TITLES_ATTR,null);
    ret.setTitlesCount(titles);
    return ret;
  }
}
