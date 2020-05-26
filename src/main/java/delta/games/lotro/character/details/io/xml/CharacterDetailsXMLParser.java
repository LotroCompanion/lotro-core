package delta.games.lotro.character.details.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.details.CharacterDetails;

/**
 * Parser for character details stored in XML.
 * @author DAM
 */
public class CharacterDetailsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed character or <code>null</code>.
   */
  public CharacterDetails parseXML(File source)
  {
    CharacterDetails details=new CharacterDetails();
    if (source.exists())
    {
      Element root=DOMParsingTools.parse(source);
      if (root!=null)
      {
        parseCharacterDetails(root,details);
      }
    }
    return details;
  }

  /**
   * Read character details attributes from a tag.
   * @param root Tag to read.
   * @param details Storage for read data.
   */
  public static void parseCharacterDetails(Element root, CharacterDetails details)
  {
    NamedNodeMap attrs=root.getAttributes();
    // In-game time
    int inGameTime=DOMParsingTools.getIntAttribute(attrs,CharacterDetailsXMLConstants.IN_GAME_TIME_ATTR,0);
    details.setIngameTime(inGameTime);
    // Money
    int money=DOMParsingTools.getIntAttribute(attrs,CharacterDetailsXMLConstants.MONEY_ATTR,0);
    details.getMoney().setRawValue(money);
    // Last logout date
    long date=DOMParsingTools.getLongAttribute(attrs,CharacterDetailsXMLConstants.LAST_LOGOUT_DATE_ATTR,0);
    Long lastLogoutDate=(date!=0)?Long.valueOf(date):null;
    details.setLastLogoutDate(lastLogoutDate);

    // XP
    Element xpTag=DOMParsingTools.getChildTagByName(root,CharacterDetailsXMLConstants.XP_TAG);
    if (xpTag!=null)
    {
      NamedNodeMap xpAttrs=xpTag.getAttributes();
      // Current XP
      int currentXp=DOMParsingTools.getIntAttribute(xpAttrs,CharacterDetailsXMLConstants.CURRENT_XP_ATTR,0);
      details.setXp(currentXp);
    }
    // Current title ID
    Element titleTag=DOMParsingTools.getChildTagByName(root,CharacterDetailsXMLConstants.CURRENT_TITLE_TAG);
    if (titleTag!=null)
    {
      NamedNodeMap titleAttrs=titleTag.getAttributes();
      // Current XP
      int titleId=DOMParsingTools.getIntAttribute(titleAttrs,CharacterDetailsXMLConstants.CURRENT_TITLE_ID_ATTR,0);
      details.setCurrentTitleId((titleId!=0)?Integer.valueOf(titleId):null);
    }
  }
}
