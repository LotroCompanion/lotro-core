package delta.games.lotro.character.reputation.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.reputation.FactionData;
import delta.games.lotro.character.reputation.ReputationData;
import delta.games.lotro.common.Faction;
import delta.games.lotro.common.FactionLevel;
import delta.games.lotro.common.Factions;

/**
 * Parser for character reputation stored in XML.
 * @author DAM
 */
public class ReputationXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public ReputationData parseXML(File source)
  {
    ReputationData h=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      h=parseReputation(root);
    }
    return h;
  }

  private ReputationData parseReputation(Element root)
  {
    ReputationData h=new ReputationData();

    List<Element> factionTags=DOMParsingTools.getChildTagsByName(root,ReputationXMLConstants.FACTION_TAG);
    for(Element factionTag : factionTags)
    {
      NamedNodeMap factionAttrs=factionTag.getAttributes();
      // Faction
      String factionKey=DOMParsingTools.getStringAttribute(factionAttrs,ReputationXMLConstants.FACTION_KEY_ATTR,null);
      String currentFactionLevelKey=DOMParsingTools.getStringAttribute(factionAttrs,ReputationXMLConstants.FACTION_CURRENT_ATTR,null);
      if ((factionKey!=null) && (currentFactionLevelKey!=null))
      {
        Faction faction=Factions.getInstance().getByKey(factionKey);
        if (faction!=null)
        {
          FactionData factionData=h.getOrCreateFactionStat(faction);
          List<Element> levelTags=DOMParsingTools.getChildTagsByName(root,ReputationXMLConstants.FACTION_LEVEL_TAG);
          for(Element levelTag : levelTags)
          {
            NamedNodeMap levelAttrs=levelTag.getAttributes();
            String levelKey=DOMParsingTools.getStringAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_KEY_ATTR,"");
            FactionLevel level=FactionLevel.getByKey(levelKey);
            long date=DOMParsingTools.getLongAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_DATE_ATTR,0);
            if ((level!=null) && (date!=0))
            {
              factionData.addUpdate(level,date);
            }
            else
            {
              // TODO warn
            }
          }
        }
        else
        {
          // TODO warn
        }
      }
      else
      {
        // TODO warn
      }
    }
    return h;
  }
}
