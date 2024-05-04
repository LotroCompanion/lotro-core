package delta.games.lotro.kinship.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.io.xml.CharacterSummaryXMLWriter;
import delta.games.lotro.kinship.KinshipCharacterSummary;
import delta.games.lotro.kinship.KinshipMember;
import delta.games.lotro.kinship.KinshipRank;
import delta.games.lotro.kinship.KinshipRoster;
import delta.games.lotro.kinship.comparators.KinshipMemberIdComparator;
import delta.games.lotro.kinship.comparators.KinshipRankComparator;

/**
 * Writes LOTRO kinship rosters to XML files.
 * @author DAM
 */
public class KinshipRosterXMLWriter
{
  /**
   * Write a kinship roster to a XML file.
   * @param outFile Output file.
   * @param roster Summary to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final KinshipRoster roster, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl rosterAttrs=new AttributesImpl();
        hd.startElement("","",KinshipRosterXMLConstants.ROSTER_TAG,rosterAttrs);
        write(hd,roster);
        hd.endElement("","",KinshipRosterXMLConstants.ROSTER_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write kinship roster.
   * @param hd Output stream.
   * @param roster Source data.
   * @throws SAXException If an error occurs.
   */
  public static void write(TransformerHandler hd, KinshipRoster roster) throws SAXException
  {
    // Ranks
    List<KinshipRank> ranks=roster.getRanks();
    Collections.sort(ranks,new KinshipRankComparator());
    for(KinshipRank rank : ranks)
    {
      writeRank(hd,rank);
    }
    // Members
    List<KinshipMember> members=roster.getAllMembers();
    Collections.sort(members,new KinshipMemberIdComparator());
    for(KinshipMember member : members)
    {
      writeMember(hd,member);
    }
  }


  private static void writeRank(TransformerHandler hd, KinshipRank rank) throws SAXException
  {
    AttributesImpl rankAttrs=new AttributesImpl();
    // Code
    int code=rank.getCode();
    rankAttrs.addAttribute("","",KinshipRosterXMLConstants.RANK_ID_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Level
    int level=rank.getLevel();
    if (level!=0)
    {
      rankAttrs.addAttribute("","",KinshipRosterXMLConstants.RANK_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(level));
    }
    // Name
    String name=rank.getName();
    if (name.length()>0)
    {
      rankAttrs.addAttribute("","",KinshipRosterXMLConstants.RANK_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",KinshipRosterXMLConstants.RANK_TAG,rankAttrs);
    hd.endElement("","",KinshipRosterXMLConstants.RANK_TAG);
  }

  private static void writeMember(TransformerHandler hd, KinshipMember member) throws SAXException
  {
    AttributesImpl memberAttrs=new AttributesImpl();
    KinshipCharacterSummary memberSummary=member.getSummary();
    CharacterSummaryXMLWriter.writeBaseCharacterSummary(memberAttrs,memberSummary);
    // Vocation
    Integer vocationId=memberSummary.getVocationID();
    if (vocationId!=null)
    {
      memberAttrs.addAttribute("","",KinshipRosterXMLConstants.MEMBER_VOCATION_ATTR,XmlWriter.CDATA,vocationId.toString());
    }
    // Area ID
    Integer areaId=memberSummary.getAreaID();
    if (areaId!=null)
    {
      memberAttrs.addAttribute("","",KinshipRosterXMLConstants.MEMBER_AREA_ID_ATTR,XmlWriter.CDATA,areaId.toString());
    }
    // Last logout date
    Long lastLogoutDate=memberSummary.getLastLogoutDate();
    if (lastLogoutDate!=null)
    {
      memberAttrs.addAttribute("","",KinshipRosterXMLConstants.MEMBER_LAST_LOGOUT_DATE_ATTR,XmlWriter.CDATA,lastLogoutDate.toString());
    }
    // Join date
    Long joinDate=member.getJoinDate();
    if (joinDate!=null)
    {
      memberAttrs.addAttribute("","",KinshipRosterXMLConstants.MEMBER_JOIN_DATE_ATTR,XmlWriter.CDATA,joinDate.toString());
    }
    // Rank
    KinshipRank rank=member.getRank();
    if (rank!=null)
    {
      memberAttrs.addAttribute("","",KinshipRosterXMLConstants.MEMBER_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank.getCode()));
    }
    // Notes
    String notes=member.getNotes();
    if (notes.length()>0)
    {
      memberAttrs.addAttribute("","",KinshipRosterXMLConstants.MEMBER_NOTES_ATTR,XmlWriter.CDATA,notes);
    }
    hd.startElement("","",KinshipRosterXMLConstants.MEMBER_TAG,memberAttrs);
    hd.endElement("","",KinshipRosterXMLConstants.MEMBER_TAG);
  }
}
