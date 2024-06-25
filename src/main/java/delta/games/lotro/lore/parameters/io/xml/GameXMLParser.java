package delta.games.lotro.lore.parameters.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.parameters.GameParameters;

/**
 * Parser for game data stored in XML.
 * @author DAM
 */
public class GameXMLParser
{
  /**
   * Parse crafting data from an XML file.
   * @param source Source file.
   * @return the parsed data.
   */
  public GameParameters parseGameData(File source)
  {
    GameParameters ret=new GameParameters();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      Element paramsTag=DOMParsingTools.getChildTagByName(root,GameXMLConstants.GAME_PARAMETERS_TAG);
      if (paramsTag!=null)
      {
        NamedNodeMap attrs=paramsTag.getAttributes();
        // Max character level
        int maxCharacterLevel=DOMParsingTools.getIntAttribute(attrs,GameXMLConstants.PARAM_MAX_CHARACTER_LEVEL_ATTR,0);
        ret.setMaxCharacterLevel(maxCharacterLevel);
        // Max legendary item level
        int maxLegendaryItemLevel=DOMParsingTools.getIntAttribute(attrs,GameXMLConstants.MAX_LEGENDARY_ITEM_LEVEL_ATTR,0);
        ret.setMaxLegendaryItemLevel(maxLegendaryItemLevel);
        // Max virtue rank
        int maxVirtueRank=DOMParsingTools.getIntAttribute(attrs,GameXMLConstants.MAX_VIRTUE_RANK_ATTR,0);
        ret.setMaxVirtueRank(maxVirtueRank);
      }
    }
    return ret;
  }
}
