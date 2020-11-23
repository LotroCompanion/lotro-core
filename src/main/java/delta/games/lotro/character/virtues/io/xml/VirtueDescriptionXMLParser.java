package delta.games.lotro.character.virtues.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;
import delta.games.lotro.utils.maths.Progression;

/**
 * Parser for virtue descriptions stored in XML.
 * @author DAM
 */
public class VirtueDescriptionXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(VirtueDescriptionXMLParser.class);

  /**
   * Parse a virtues XML file.
   * @param source Source file.
   * @return List of parsed virtues.
   */
  public static List<VirtueDescription> parseVirtuesFile(File source)
  {
    List<VirtueDescription> virtues=new ArrayList<VirtueDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> virtueTags=DOMParsingTools.getChildTagsByName(root,VirtueDescriptionXMLConstants.VIRTUE_TAG);
      for(Element virtueTag : virtueTags)
      {
        VirtueDescription virtue=parseVirtue(virtueTag);
        virtues.add(virtue);
      }
    }
    return virtues;
  }

  /**
   * Build a virtue description from an XML tag.
   * @param root Root XML tag.
   * @return A virtue description.
   */
  private static VirtueDescription parseVirtue(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    VirtueDescription virtue=new VirtueDescription();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,VirtueDescriptionXMLConstants.VIRTUE_IDENTIFIER_ATTR,0);
    virtue.setIdentifier(id);
    // Key
    String key=DOMParsingTools.getStringAttribute(attrs,VirtueDescriptionXMLConstants.VIRTUE_KEY_ATTR,"");
    virtue.setKey(key);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,VirtueDescriptionXMLConstants.VIRTUE_NAME_ATTR,null);
    virtue.setName(name);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,VirtueDescriptionXMLConstants.VIRTUE_ICON_ID_ATTR,0);
    virtue.setIconId(iconId);
    // Rank stat key
    String rankStatKey=DOMParsingTools.getStringAttribute(attrs,VirtueDescriptionXMLConstants.VIRTUE_RANK_STAT_KEY_ATTR,null);
    virtue.setRankStatKey(rankStatKey);
    // XP property
    String xpPropertyName=DOMParsingTools.getStringAttribute(attrs,VirtueDescriptionXMLConstants.VIRTUE_XP_PROPERTY_ATTR,"");
    virtue.setXpPropertyName(xpPropertyName);
    // Max rank progression
    int maxRankProgressionId=DOMParsingTools.getIntAttribute(attrs,VirtueDescriptionXMLConstants.VIRTUE_MAX_RANK_PROGRESSION_ATTR,0);
    Progression maxRankProgression=ProgressionsManager.getInstance().getProgression(maxRankProgressionId);
    virtue.setMaxRankForCharacterLevelProgression(maxRankProgression);
    if (maxRankProgression==null)
    {
      LOGGER.warn("Could not load progression "+maxRankProgressionId+" for virtue: "+key);
    }
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,VirtueDescriptionXMLConstants.VIRTUE_DESCRIPTION_ATTR,"");
    virtue.setDescription(description);
    // Active stats
    Element activeStatsTag=DOMParsingTools.getChildTagByName(root,VirtueDescriptionXMLConstants.ACTIVE_STATS_TAG);
    StatsProviderXMLParser.parseStatsProvider(activeStatsTag,virtue.getStatsProvider());
    // Passive stats
    Element passiveStatsTag=DOMParsingTools.getChildTagByName(root,VirtueDescriptionXMLConstants.PASSIVE_STATS_TAG);
    StatsProviderXMLParser.parseStatsProvider(passiveStatsTag,virtue.getPassiveStatsProvider());
    // XP table
    List<Element> xpTags=DOMParsingTools.getChildTagsByName(root,VirtueDescriptionXMLConstants.XP_TAG);
    for(Element xpTag : xpTags)
    {
      NamedNodeMap xpAttrs=xpTag.getAttributes();
      int tier=DOMParsingTools.getIntAttribute(xpAttrs,VirtueDescriptionXMLConstants.XP_TIER_ATTR,0);
      int value=DOMParsingTools.getIntAttribute(xpAttrs,VirtueDescriptionXMLConstants.XP_VALUE_ATTR,0);
      virtue.setXpForTier(tier,value);
    }
    return virtue;
  }
}
