package delta.games.lotro.common.difficulty.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.difficulty.Difficulty;

/**
 * Parser for difficulties stored in XML.
 * @author DAM
 */
public class DifficultyXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed difficulties.
   */
  public static List<Difficulty> parseXML(File source)
  {
    List<Difficulty> ret=new ArrayList<Difficulty>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> difficultyTags=DOMParsingTools.getChildTagsByName(root,DifficultyXMLConstants.DIFFICULTY_TAG);
      for(Element difficultyTag : difficultyTags)
      {
        Difficulty difficulty=parseDifficulty(difficultyTag);
        ret.add(difficulty);
      }
    }
    return ret;
  }

  private static Difficulty parseDifficulty(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Code
    int code=DOMParsingTools.getIntAttribute(attrs,DifficultyXMLConstants.DIFFICULTY_CODE_ATTR,0);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,DifficultyXMLConstants.DIFFICULTY_NAME_ATTR,null);
    return new Difficulty(code,name);
  }
}
