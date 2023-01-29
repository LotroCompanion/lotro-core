package delta.games.lotro.character.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.account.AccountReference;
import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.CharacterDataSummary;
import delta.games.lotro.character.CharacterReference;
import delta.games.lotro.character.CharacterSummary;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.races.NationalitiesManager;
import delta.games.lotro.character.races.NationalityDescription;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.common.CharacterSex;
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
    NamedNodeMap attrs=root.getAttributes();
    // Nationality
    Integer nationalityCode=DOMParsingTools.getIntegerAttribute(attrs,CharacterXMLConstants.CHARACTER_NATIONALITY_CODE_ATTR,null);
    if (nationalityCode!=null)
    {
      NationalityDescription nationality=NationalitiesManager.getInstance().getNationalityDescription(nationalityCode.intValue());
      summary.setNationality(nationality);
    }
    else
    {
      // Region
      String region=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.CHARACTER_REGION_ATTR,"");
      if (region.length()>0)
      {
        NationalityDescription nationality=NationalitiesManager.getInstance().getNationalityDescriptionByName(region);
        summary.setNationality(nationality);
      }
    }
    // Kinship ID
    String kinshipIDStr=DOMParsingTools.getStringAttribute(attrs,CharacterXMLConstants.CHARACTER_KINSHIP_ID_ATTR,null);
    if (kinshipIDStr!=null)
    {
      InternalGameId kinshipID=InternalGameId.fromString(kinshipIDStr);
      summary.setKinshipID(kinshipID);
    }
    // Import date
    long importDate=DOMParsingTools.getLongAttribute(attrs,CharacterXMLConstants.CHARACTER_IMPORT_DATE_ATTR,0);
    if (importDate!=0)
    {
      summary.setImportDate(Long.valueOf(importDate));
    }
  }

  /**
   * Read character summary attributes from a tag.
   * @param root Tag to read.
   * @param summary Summary to write to.
   */
  public static void parseBaseCharacterSummary(Element root, BaseCharacterSummary summary)
  {
    parseCharacterReference(root,summary);
    // Server
    String server=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_SERVER_ATTR,"");
    summary.setServer(server);
    // Account
    // - name
    String accountName=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_ACCOUNT_NAME_ATTR,"");
    // - subscription
    String subscription=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_SUBSCRIPTION_KEY_ATTR,"");
    AccountReference id=new AccountReference(accountName,subscription);
    summary.setAccountID(id);
    // Race
    String raceStr=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_RACE_ATTR,"");
    RaceDescription race=RacesManager.getInstance().getByPersistenceKey(raceStr);
    summary.setRace(race);
    // Sex
    String sexKey=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_SEX_ATTR,"");
    if (sexKey!=null)
    {
      CharacterSex sex=CharacterSex.getByKey(sexKey); 
      summary.setCharacterSex(sex);
    }
  }

  /**
   * Read character summary attributes from a tag.
   * @param root Tag to read.
   * @param summary Summary to write to.
   */
  public static void parseCharacterReference(Element root, CharacterReference summary)
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
    // Class
    String classKey=DOMParsingTools.getStringAttribute(root.getAttributes(),CharacterXMLConstants.CHARACTER_CLASS_ATTR,"");
    ClassDescription characterClass=ClassesManager.getInstance().getCharacterClassByKey(classKey);
    summary.setCharacterClass(characterClass);
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
