package delta.games.lotro.character.social.friends.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.io.xml.CharacterSummaryXMLParser;
import delta.games.lotro.character.social.friends.Friend;
import delta.games.lotro.character.social.friends.FriendsManager;

/**
 * Parser for friends stored in XML.
 * @author DAM
 */
public class FriendsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return the loaded friends or <code>null</code>.
   */
  public FriendsManager parseXML(File source)
  {
    FriendsManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseFriendsManager(root);
    }
    return ret;
  }

  private static FriendsManager parseFriendsManager(Element mainTag)
  {
    FriendsManager ret=new FriendsManager();
    // Friends
    List<Element> friendTags=DOMParsingTools.getChildTagsByName(mainTag,FriendsXMLConstants.FRIEND_TAG,false);
    for(Element friendTag : friendTags)
    {
      Friend friend=parseFriend(friendTag);
      ret.addFriend(friend);
    }
    return ret;
  }

  /**
   * Read a friend from a tag.
   * @param friendTag Tag to read.
   * @return the loaded friend or <code>null</code>.
   */
  private static Friend parseFriend(Element friendTag)
  {
    NamedNodeMap attrs=friendTag.getAttributes();
    Friend friend=new Friend();
    CharacterSummaryXMLParser.parseCharacterReference(friendTag,friend);
    // Vocation ID
    int vocationID=DOMParsingTools.getIntAttribute(attrs,FriendsXMLConstants.FRIEND_VOCATION_ATTR,0);
    if (vocationID!=0)
    {
      friend.setVocationID(Integer.valueOf(vocationID));
    }
    // Area ID
    int areaID=DOMParsingTools.getIntAttribute(attrs,FriendsXMLConstants.FRIEND_AREA_ID_ATTR,0);
    if (areaID!=0)
    {
      friend.setAreaID(Integer.valueOf(areaID));
    }
    // Last logout date
    long lastLogoutDate=DOMParsingTools.getLongAttribute(attrs,FriendsXMLConstants.FRIEND_LAST_LOGOUT_DATE_ATTR,0);
    if (lastLogoutDate!=0)
    {
      friend.setLastLogoutDate(Long.valueOf(lastLogoutDate));
    }
    // Notes
    String notes=DOMParsingTools.getStringAttribute(attrs,FriendsXMLConstants.FRIEND_NOTES_ATTR,"");
    friend.setNote(notes);
    return friend;
  }
}
