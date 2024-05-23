package delta.games.lotro.character.pvp.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.pvp.PVPStatus;
import delta.games.lotro.lore.pvp.Rank;
import delta.games.lotro.lore.pvp.RankScale;
import delta.games.lotro.lore.pvp.RankScaleKeys;
import delta.games.lotro.lore.pvp.RanksManager;

/**
 * Parser for the PVP status stored in XML.
 * @author DAM
 */
public class PVPStatusXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public PVPStatus parseXML(File source)
  {
    PVPStatus ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseStatus(root);
    }
    return ret;
  }

  private PVPStatus parseStatus(Element root)
  {
    PVPStatus ret=new PVPStatus();

    RanksManager ranksMgr=RanksManager.getInstance();

    NamedNodeMap attrs=root.getAttributes();
    // Glory points
    int glory=DOMParsingTools.getIntAttribute(attrs,PVPStatusXMLConstants.GLORY_POINTS_ATTR,0);
    ret.setGlory(glory);
    // Rank
    RankScale renown=ranksMgr.getRankScale(RankScaleKeys.RENOWN);
    int rankCode=DOMParsingTools.getIntAttribute(attrs,PVPStatusXMLConstants.RANK_ATTR,0);
    Rank rank=renown.getRank(rankCode);
    ret.setRank(rank);
    // Rating
    float rating=DOMParsingTools.getFloatAttribute(attrs,PVPStatusXMLConstants.RATING_ATTR,0);
    ret.setRating(rating);
    // Prestige
    RankScale prestigeScale=ranksMgr.getRankScale(RankScaleKeys.PRESTIGE);
    int prestigeCode=DOMParsingTools.getIntAttribute(attrs,PVPStatusXMLConstants.PRESTIGE_ATTR,0);
    Rank prestige=prestigeScale.getRank(prestigeCode);
    ret.setPrestige(prestige);
    // Deaths
    int deaths=DOMParsingTools.getIntAttribute(attrs,PVPStatusXMLConstants.DEATHS_ATTR,0);
    ret.setDeaths(deaths);
    // Kills
    int kills=DOMParsingTools.getIntAttribute(attrs,PVPStatusXMLConstants.KILLS_ATTR,0);
    ret.setKills(kills);
    // Kills above rating
    int killsAboveRating=DOMParsingTools.getIntAttribute(attrs,PVPStatusXMLConstants.KILLS_ABOVE_RATING_ATTR,0);
    ret.setKillsAboveRating(killsAboveRating);
    // Kills below rating
    int killsBelowRating=DOMParsingTools.getIntAttribute(attrs,PVPStatusXMLConstants.KILLS_BELOW_RATING_ATTR,0);
    ret.setKillsBelowRating(killsBelowRating);
    // Kills to deaths ratio
    float kills2deathsRatio=DOMParsingTools.getFloatAttribute(attrs,PVPStatusXMLConstants.KILL_TO_DEATH_RATIO_ATTR,1);
    ret.setKill2deathRatio(kills2deathsRatio);
    // Highest rating killed
    float highestRatingKilled=DOMParsingTools.getFloatAttribute(attrs,PVPStatusXMLConstants.HIGHEST_RATING_KILLED_ATTR,0);
    ret.setHighestRatingKilled(highestRatingKilled);
    // Death blows
    int deathBlows=DOMParsingTools.getIntAttribute(attrs,PVPStatusXMLConstants.DEATH_BLOWS_ATTR,0);
    ret.setDeathBlows(deathBlows);
    return ret;
  }
}
