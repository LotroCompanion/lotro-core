package delta.games.lotro.lore.items.legendary2.global.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.lore.items.legendary2.global.LegendaryData2;
import delta.games.lotro.utils.maths.Progression;

/**
 * Parser for data of the legendary system (reloaded) stored in XML.
 * @author DAM
 */
public class LegendaryData2XMLParser
{
  /**
   * Parse a legendary data XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public static LegendaryData2 parseLegendaryDataFile(File source)
  {
    LegendaryData2 ret=new LegendaryData2();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      NamedNodeMap attrs=root.getAttributes();
      // Character level to item level progression
      int progressionID=DOMParsingTools.getIntAttribute(attrs,LegendaryData2XMLConstants.CHARACTER_LEVEL_TO_ITEM_LEVEL_PROGRESSION_ID_ATTR,0);
      Progression progression=ProgressionsManager.getInstance().getProgression(progressionID);
      ret.setCharacterLevel2ItemLevelProgression(progression);
    }
    return ret;
  }
}
