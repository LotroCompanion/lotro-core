package delta.games.lotro.character.io.xml;

import java.io.File;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.common.Race;

/**
 * Parser for character summary stored in XML.
 * @author DAM
 */
public class CharacterSummaryXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed character or <code>null</code>.
   */
  public CharacterSummary parseXML(File source)
  {
    CharacterSummary summary=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      summary=new CharacterSummary();
      parseCharacter(root, summary);
    }
    return summary;
  }

  /**
   * Read character summary attributes from a tag.
   * @param root Tag to read.
   * @param summary Summary to write to.
   */
  public static void parseCharacter(Element root, CharacterSummary summary)
  {
    // Name
    String name=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_NAME_ATTR,"");
    summary.setName(name);
    // Server
    String server=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_SERVER_ATTR,"");
    summary.setServer(server);
    // Account name
    String accountName=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_ACCOUNT_ATTR,"");
    summary.setAccountName(accountName);
    // Class
    String characterClass=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_CLASS_ATTR,"");
    CharacterClass cClass=CharacterClass.getByKey(characterClass);
    summary.setCharacterClass(cClass);
    // Race
    String race=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_RACE_ATTR,"");
    Race cRace=Race.getByLabel(race); 
    summary.setRace(cRace);
    // Sex
    String sexKey=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_SEX_ATTR,"");
    if (sexKey!=null)
    {
      CharacterSex sex=CharacterSex.getByKey(sexKey); 
      summary.setCharacterSex(sex);
    }
    // Region
    String region=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_REGION_ATTR,"");
    summary.setRegion(region);
    // Level
    int level=DOMParsingTools.getIntAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_LEVEL_ATTR,0);
    summary.setLevel(level);
  }
}
