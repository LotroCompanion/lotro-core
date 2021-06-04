package delta.games.lotro.character.io.xml;

import java.io.File;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.CharacterDataSummary;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.id.InternalGameId;

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
      parseCharacterSummary(root, summary);
    }
    return summary;
  }

  /**
   * Read character summary attributes from a tag.
   * @param root Tag to read.
   * @param summary Summary to write to.
   */
  public static void parseCharacterSummary(Element root, CharacterSummary summary)
  {
    parseBaseCharacterSummary(root,summary);
    // Region
    String region=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_REGION_ATTR,"");
    summary.setRegion(region);
  }

  /**
   * Read character summary attributes from a tag.
   * @param root Tag to read.
   * @param summary Summary to write to.
   */
  public static void parseBaseCharacterSummary(Element root, BaseCharacterSummary summary)
  {
    // ID
    InternalGameId id=null;
    String idStr=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_ID_ATTR,null);
    if (idStr!=null)
    {
      id=InternalGameId.fromString(idStr);
      summary.setId(id);
    }
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
    // Level
    int level=DOMParsingTools.getIntAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_LEVEL_ATTR,0);
    summary.setLevel(level);
  }

  /**
   * Read character summary attributes from a tag.
   * @param root Tag to read.
   * @param summary Summary to write to.
   */
  public static void parseCharacterDataSummary(Element root, CharacterDataSummary summary)
  {
    // Name
    String name=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_NAME_ATTR,"");
    summary.setName(name);
    // Level
    int level=DOMParsingTools.getIntAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_LEVEL_ATTR,0);
    summary.setLevel(level);
  }
}
