package delta.games.lotro.kinship.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.io.xml.CharacterSummaryXMLParser;
import delta.games.lotro.kinship.KinshipCharacterSummary;
import delta.games.lotro.kinship.KinshipMember;
import delta.games.lotro.kinship.KinshipRank;
import delta.games.lotro.kinship.KinshipRoster;

/**
 * Parser for kinship summaries stored in XML.
 * @author DAM
 */
public class KinshipRosterXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return the loaded summary or <code>null</code>.
   */
  public KinshipRoster parseXML(File source)
  {
    KinshipRoster ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseKinshipRoster(root);
    }
    return ret;
  }

  private static KinshipRoster parseKinshipRoster(Element rosterTag)
  {
    KinshipRoster roster=new KinshipRoster();
    // Ranks
    List<Element> rankTags=DOMParsingTools.getChildTagsByName(rosterTag,KinshipRosterXMLConstants.RANK_TAG,false);
    for(Element rankTag : rankTags)
    {
      KinshipRank rank=parseKinshipRank(rankTag);
      roster.addRank(rank);
    }
    // Members
    List<Element> memberTags=DOMParsingTools.getChildTagsByName(rosterTag,KinshipRosterXMLConstants.MEMBER_TAG,false);
    for(Element memberTag : memberTags)
    {
      KinshipMember member=parseKinshipMember(roster,memberTag);
      roster.addMember(member);
    }
    return roster;
  }

  /**
   * Read a kinship rank from a tag.
   * @param rankTag Tag to read.
   * @return the loaded rank or <code>null</code>.
   */
  private static KinshipRank parseKinshipRank(Element rankTag)
  {
    NamedNodeMap attrs=rankTag.getAttributes();
    // ID
    int code=DOMParsingTools.getIntAttribute(attrs,KinshipRosterXMLConstants.RANK_ID_ATTR,0);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,KinshipRosterXMLConstants.RANK_NAME_ATTR,"");
    KinshipRank rank=new KinshipRank(code,name);
    return rank;
  }

  /**
   * Read a kinship member from a tag.
   * @param roster Roster.
   * @param memberTag Tag to read.
   * @return the loaded member or <code>null</code>.
   */
  private static KinshipMember parseKinshipMember(KinshipRoster roster, Element memberTag)
  {
    NamedNodeMap attrs=memberTag.getAttributes();
    KinshipMember member=new KinshipMember();
    KinshipCharacterSummary summary=member.getSummary();
    CharacterSummaryXMLParser.parseBaseCharacterSummary(memberTag,summary);
    // Vocation ID
    int vocationID=DOMParsingTools.getIntAttribute(attrs,KinshipRosterXMLConstants.MEMBER_VOCATION_ATTR,0);
    if (vocationID!=0)
    {
      summary.setVocationID(Integer.valueOf(vocationID));
    }
    // Area ID
    int areaID=DOMParsingTools.getIntAttribute(attrs,KinshipRosterXMLConstants.MEMBER_AREA_ID_ATTR,0);
    if (areaID!=0)
    {
      summary.setAreaID(Integer.valueOf(areaID));
    }
    // Last logout date
    long lastLogoutDate=DOMParsingTools.getLongAttribute(attrs,KinshipRosterXMLConstants.MEMBER_LAST_LOGOUT_DATE_ATTR,0);
    if (lastLogoutDate!=0)
    {
      summary.setLastLogoutDate(Long.valueOf(lastLogoutDate));
    }
    // Join date
    long joinDate=DOMParsingTools.getLongAttribute(attrs,KinshipRosterXMLConstants.MEMBER_JOIN_DATE_ATTR,0);
    if (joinDate!=0)
    {
      member.setJoinDate(Long.valueOf(joinDate));
    }
    // Rank
    int rankCode=DOMParsingTools.getIntAttribute(attrs,KinshipRosterXMLConstants.MEMBER_RANK_ATTR,-1);
    if (rankCode!=-1)
    {
      KinshipRank rank=roster.getRankByCode(rankCode);
      member.setRank(rank);
    }
    // Notes
    String notes=DOMParsingTools.getStringAttribute(attrs,KinshipRosterXMLConstants.MEMBER_NOTES_ATTR,"");
    member.setNotes(notes);
    return member;
  }
}
