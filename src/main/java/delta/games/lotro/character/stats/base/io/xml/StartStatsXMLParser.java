package delta.games.lotro.character.stats.base.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.StartStatsManager;

/**
 * Parser for start stats stored in XML.
 * @author DAM
 */
public class StartStatsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public StartStatsManager parseXML(File source)
  {
    StartStatsManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseStartStats(root);
    }
    return ret;
  }

  private StartStatsManager parseStartStats(Element root)
  {
    StartStatsManager ret=new StartStatsManager();
    List<Element> statsTags=DOMParsingTools.getChildTagsByName(root,StartStatsXMLConstants.STATS_TAG);
    for(Element statsTag : statsTags)
    {
      // Class
      String classKey=DOMParsingTools.getStringAttribute(statsTag.getAttributes(),StartStatsXMLConstants.STATS_CLASS_ATTR,"");
      ClassDescription characterClass=ClassesManager.getInstance().getByKey(classKey);
      // Level
      int level=DOMParsingTools.getIntAttribute(statsTag.getAttributes(),StartStatsXMLConstants.STATS_LEVEL_ATTR,0);
      // Stats
      BasicStatsSet stats=BasicStatsSetXMLParser.parseStats(statsTag);
      ret.setStats(characterClass,level,stats);
    }
    return ret;
  }
}
