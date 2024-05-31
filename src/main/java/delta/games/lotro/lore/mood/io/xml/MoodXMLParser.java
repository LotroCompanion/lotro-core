package delta.games.lotro.lore.mood.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.mood.MoodEntry;

/**
 * Parser for mood data stored in XML.
 * @author DAM
 */
public class MoodXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public List<MoodEntry> parseXML(File source)
  {
    List<MoodEntry> ret=new ArrayList<MoodEntry>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> moodTags=DOMParsingTools.getChildTagsByName(root,MoodXMLConstants.MOOD_TAG);
      for(Element moodTag : moodTags)
      {
        NamedNodeMap attrs=moodTag.getAttributes();
        // Level
        int level=DOMParsingTools.getIntAttribute(attrs,MoodXMLConstants.MOOD_LEVEL_ATTR,0);
        // Morale modifier
        float moraleModifier=DOMParsingTools.getFloatAttribute(attrs,MoodXMLConstants.MOOD_MORALE_MODIFIER,0);
        MoodEntry mood=new MoodEntry(level,moraleModifier);
       ret.add(mood);
      }
    }
    return ret;
  }
}
