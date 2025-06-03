package delta.games.lotro.character.details.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.details.CharacterDetails;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.geo.io.xml.PositionXMLConstants;
import delta.games.lotro.common.geo.io.xml.PositionXMLParser;

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
    // 'No Purchase Required'
    Boolean noPurchaseRequired=DOMParsingTools.getBooleanAttribute(attrs,CharacterDetailsXMLConstants.NO_PURCHASE_REQUIRED_ATTR,null);
    details.setNoPurchaseRequired(noPurchaseRequired);

    // XP
    Element xpTag=DOMParsingTools.getChildTagByName(root,CharacterDetailsXMLConstants.XP_TAG);
    if (xpTag!=null)
    {
      NamedNodeMap xpAttrs=xpTag.getAttributes();
      // Current XP
      int currentXp=DOMParsingTools.getIntAttribute(xpAttrs,CharacterDetailsXMLConstants.CURRENT_XP_ATTR,0);
      details.setXp(currentXp);
    }
    // Current title
    Element titleTag=DOMParsingTools.getChildTagByName(root,CharacterDetailsXMLConstants.CURRENT_TITLE_TAG);
    if (titleTag!=null)
    {
      NamedNodeMap titleAttrs=titleTag.getAttributes();
      int titleId=DOMParsingTools.getIntAttribute(titleAttrs,CharacterDetailsXMLConstants.CURRENT_TITLE_ID_ATTR,0);
      details.setCurrentTitleId((titleId!=0)?Integer.valueOf(titleId):null);
    }
    // Current area
    Element areaTag=DOMParsingTools.getChildTagByName(root,CharacterDetailsXMLConstants.CURRENT_AREA_TAG);
    if (areaTag!=null)
    {
      NamedNodeMap areaAttrs=areaTag.getAttributes();
      int areaID=DOMParsingTools.getIntAttribute(areaAttrs,CharacterDetailsXMLConstants.CURRENT_AREA_ID_ATTR,0);
      details.setAreaID((areaID!=0)?Integer.valueOf(areaID):null);
    }
    // Current dungeon
    Element dungeonTag=DOMParsingTools.getChildTagByName(root,CharacterDetailsXMLConstants.CURRENT_DUNGEON_TAG);
    if (dungeonTag!=null)
    {
      NamedNodeMap dungeonAttrs=dungeonTag.getAttributes();
      int dungeonID=DOMParsingTools.getIntAttribute(dungeonAttrs,CharacterDetailsXMLConstants.CURRENT_DUNGEON_ID_ATTR,0);
      details.setDungeonID((dungeonID!=0)?Integer.valueOf(dungeonID):null);
    }
    // Position
    Element positionTag=DOMParsingTools.getChildTagByName(root,PositionXMLConstants.POSITION);
    if (positionTag!=null)
    {
      Position position=PositionXMLParser.parseSimplePosition(positionTag);
      details.setPosition(position);
    }
    // Current vocation
    Element vocationTag=DOMParsingTools.getChildTagByName(root,CharacterDetailsXMLConstants.CURRENT_VOCATION_TAG);
    if (vocationTag!=null)
    {
      NamedNodeMap vocationAttrs=vocationTag.getAttributes();
      int vocationId=DOMParsingTools.getIntAttribute(vocationAttrs,CharacterDetailsXMLConstants.CURRENT_VOCATION_ID_ATTR,0);
      details.setCurrentVocationId((vocationId!=0)?Integer.valueOf(vocationId):null);
    }
  }
}
