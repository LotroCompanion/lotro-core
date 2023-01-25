package delta.games.lotro.character.stats.base.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.stats.base.DerivedStatsContributionsMgr;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsRegistry;

/**
 * Parser for derived stat contributions stored in XML.
 * @author DAM
 */
public class DerivedStatsContributionsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public DerivedStatsContributionsMgr parseXML(File source)
  {
    DerivedStatsContributionsMgr ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseContributions(root);
    }
    return ret;
  }

  private DerivedStatsContributionsMgr parseContributions(Element root)
  {
    StatsRegistry statsRegistry=StatsRegistry.getInstance();
    DerivedStatsContributionsMgr ret=new DerivedStatsContributionsMgr();
    List<Element> classContribsTags=DOMParsingTools.getChildTagsByName(root,DerivedStatsContributionsXMLConstants.CLASS_CONTRIBS_TAG);
    for(Element classContribsTag : classContribsTags)
    {
      // Class
      String classKey=DOMParsingTools.getStringAttribute(classContribsTag.getAttributes(),DerivedStatsContributionsXMLConstants.CLASS_CONTRIBS_CLASS_ATTR,"");
      ClassDescription characterClass=ClassesManager.getInstance().getCharacterClassByKey(classKey);
      List<Element> statContribsTags=DOMParsingTools.getChildTagsByName(classContribsTag,DerivedStatsContributionsXMLConstants.STAT_CONTRIBS_TAG);
      for(Element statContribsTag : statContribsTags)
      {
        // Source stat
        String sourceStatStr=DOMParsingTools.getStringAttribute(statContribsTag.getAttributes(),DerivedStatsContributionsXMLConstants.STAT_CONTRIBS_SOURCE_STAT_ATTR,"");
        StatDescription sourceStat=statsRegistry.getByKey(sourceStatStr);
        // Contributions
        List<Element> statContribTags=DOMParsingTools.getChildTagsByName(statContribsTag,DerivedStatsContributionsXMLConstants.STAT_CONTRIB_TAG);
        for(Element statContribTag : statContribTags)
        {
          // Target stat
          String targetStatStr=DOMParsingTools.getStringAttribute(statContribTag.getAttributes(),DerivedStatsContributionsXMLConstants.STAT_CONTRIB_TARGET_STAT_ATTR,"");
          StatDescription targetStat=statsRegistry.getByKey(targetStatStr);
          float factor=DOMParsingTools.getFloatAttribute(statContribTag.getAttributes(),DerivedStatsContributionsXMLConstants.STAT_CONTRIB_FACTOR_ATTR,0.0f);
          ret.setFactor(sourceStat,targetStat,characterClass,Float.valueOf(factor));
        }
      }
    }
    return ret;
  }
}
