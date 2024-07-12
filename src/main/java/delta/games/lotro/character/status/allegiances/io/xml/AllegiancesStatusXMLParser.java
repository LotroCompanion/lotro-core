package delta.games.lotro.character.status.allegiances.io.xml;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.allegiances.AllegianceStatus;
import delta.games.lotro.character.status.allegiances.AllegiancesStatusManager;
import delta.games.lotro.lore.allegiances.AllegianceDescription;
import delta.games.lotro.lore.allegiances.AllegiancesManager;
import delta.games.lotro.lore.allegiances.Points2LevelCurve;

/**
 * Parser for the allegiances status stored in XML.
 * @author DAM
 */
public class AllegiancesStatusXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(AllegiancesStatusXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public AllegiancesStatusManager parseXML(File source)
  {
    AllegiancesStatusManager status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseStatus(root);
    }
    return status;
  }

  private AllegiancesStatusManager parseStatus(Element root)
  {
    AllegiancesStatusManager status=new AllegiancesStatusManager();
    // Current allegiance
    NamedNodeMap attrs=root.getAttributes();
    int currentAllegianceID=DOMParsingTools.getIntAttribute(attrs,AllegiancesStatusXMLConstants.CURRENT_ALLEGIANCE_ID_ATTR,0);
    if (currentAllegianceID!=0)
    {
      AllegiancesManager mgr=AllegiancesManager.getInstance();
      AllegianceDescription currentAllegiance=mgr.getAllegiance(currentAllegianceID);
      status.setCurrentAllegiance(currentAllegiance);
    }
    // Status of allegiances
    List<Element> statusTags=DOMParsingTools.getChildTagsByName(root,AllegiancesStatusXMLConstants.ALLEGIANCE,false);
    for(Element statusTag : statusTags)
    {
      parseAllegianceStatus(status,statusTag);
    }
    return status;
  }

  private void parseAllegianceStatus(AllegiancesStatusManager status, Element statusTag)
  {
    NamedNodeMap attrs=statusTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,AllegiancesStatusXMLConstants.ALLEGIANCE_ID_ATTR,0);
    // Create status
    AllegiancesManager mgr=AllegiancesManager.getInstance();
    AllegianceDescription allegiance=mgr.getAllegiance(id);
    if (allegiance==null)
    {
      // Unknown allegiance!
      LOGGER.warn("Unknown allegiance: "+id);
      return;
    }
    AllegianceStatus newStatus=status.get(allegiance,true);
    newStatus.setStarted(true);
    // Curve
    int curveID=DOMParsingTools.getIntAttribute(attrs,AllegiancesStatusXMLConstants.ALLEGIANCE_CURVE_ID_ATTR,0);
    if (curveID!=0)
    {
      Points2LevelCurve curve=mgr.getCurvesManager().getCurve(curveID);
      newStatus.setPoints2LevelCurve(curve);
    }
    // Points
    int points=DOMParsingTools.getIntAttribute(attrs,AllegiancesStatusXMLConstants.ALLEGIANCE_POINTS_EARNED_ATTR,0);
    newStatus.setPointsEarned(points);
    // Claimed rewards
    int claimedRewardsFlags=DOMParsingTools.getIntAttribute(attrs,AllegiancesStatusXMLConstants.ALLEGIANCE_CLAIMED_REWARDS_ATTR,0);
    newStatus.setClaimedRewardsFlags(claimedRewardsFlags);
  }
}
