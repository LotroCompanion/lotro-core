package delta.games.lotro.character.stats.base.io.xml;

import java.net.URL;
import java.util.List;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.CharacterStat;
import delta.games.lotro.character.CharacterStat.STAT;
import delta.games.lotro.character.stats.base.StarterStatsManager;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Read starter stats from XML files.
 * @author DAM
 */
public class StarterStatsParser
{
  /**
   * Parse the XML file.
   * @param source Source URL.
   * @return Parsed data or <code>null</code>.
   */
  public StarterStatsManager parseXML(URL source)
  {
    StarterStatsManager stats=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      stats=parseStats(root);
    }
    return stats;
  }

  private StarterStatsManager parseStats(Element root)
  {
    StarterStatsManager mgr=new StarterStatsManager();
    List<Element> raceTags=DOMParsingTools.getChildTagsByName(root,StarterStatsXMLConstants.RACE_TAG);
    for(Element raceTag : raceTags)
    {
      String raceStr=DOMParsingTools.getStringAttribute(raceTag.getAttributes(),StarterStatsXMLConstants.RACE_NAME_ATTR,"");
      Race race=Race.getByKey(raceStr);
      List<Element> classTags=DOMParsingTools.getChildTagsByName(raceTag,StarterStatsXMLConstants.CLASS_TAG);
      for(Element classTag : classTags)
      {
        String className=DOMParsingTools.getStringAttribute(classTag.getAttributes(),StarterStatsXMLConstants.CLASS_NAME_ATTR,"");
        CharacterClass cClass=CharacterClass.getByKey(className);
        List<Element> statTags=DOMParsingTools.getChildTagsByName(classTag,StarterStatsXMLConstants.STAT_TAG);
        for(Element statTag : statTags)
        {
          String statName=DOMParsingTools.getStringAttribute(statTag.getAttributes(),StarterStatsXMLConstants.STAT_NAME_ATTR,"");
          String statValue=DOMParsingTools.getStringAttribute(statTag.getAttributes(),StarterStatsXMLConstants.STAT_VALUE_ATTR,"");
          STAT stat=STAT.valueOf(statName);
          FixedDecimalsInteger value=FixedDecimalsInteger.fromString(statValue);
          CharacterStat characterStat=new CharacterStat(stat,value);
          mgr.setStat(race,cClass,characterStat);
        }
      }
    }
    return mgr;
  }
}
