package delta.games.lotro.account.status.friends.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.account.status.friends.Friend;
import delta.games.lotro.account.status.friends.FriendsManager;
import delta.games.lotro.character.io.xml.CharacterSummaryXMLWriter;
import delta.games.lotro.common.comparators.NamedComparator;

/**
 * Writes LOTRO friends to XML files.
 * @author DAM
 */
public class FriendsXMLWriter
{
  /**
   * Write friends to a XML file.
   * @param outFile Output file.
   * @param friendsMgr Friends to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final FriendsManager friendsMgr, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl attrs=new AttributesImpl();
        hd.startElement("","",FriendsXMLConstants.FRIENDS_TAG,attrs);
        write(hd,friendsMgr);
        hd.endElement("","",FriendsXMLConstants.FRIENDS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write friends.
   * @param hd Output stream.
   * @param friendsMgr Friends to write.
   * @throws SAXException If an error occurs.
   */
  public static void write(TransformerHandler hd, FriendsManager friendsMgr) throws SAXException
  {
    // Friends
    List<Friend> friends=friendsMgr.getAll();
    Collections.sort(friends,new NamedComparator());
    for(Friend friend : friends)
    {
      writeFriend(hd,friend);
    }
  }

  private static void writeFriend(TransformerHandler hd, Friend friend) throws SAXException
  {
    AttributesImpl friendAttrs=new AttributesImpl();
    CharacterSummaryXMLWriter.writeCharacterReferenceSummary(friendAttrs,friend);
    // Vocation
    Integer vocationId=friend.getVocationID();
    if (vocationId!=null)
    {
      friendAttrs.addAttribute("","",FriendsXMLConstants.FRIEND_VOCATION_ATTR,XmlWriter.CDATA,vocationId.toString());
    }
    // Area ID
    Integer areaId=friend.getAreaID();
    if (areaId!=null)
    {
      friendAttrs.addAttribute("","",FriendsXMLConstants.FRIEND_AREA_ID_ATTR,XmlWriter.CDATA,areaId.toString());
    }
    // Last logout date
    Long lastLogoutDate=friend.getLastLogoutDate();
    if (lastLogoutDate!=null)
    {
      friendAttrs.addAttribute("","",FriendsXMLConstants.FRIEND_LAST_LOGOUT_DATE_ATTR,XmlWriter.CDATA,lastLogoutDate.toString());
    }
    // Notes
    String notes=friend.getNote();
    if (notes.length()>0)
    {
      friendAttrs.addAttribute("","",FriendsXMLConstants.FRIEND_NOTES_ATTR,XmlWriter.CDATA,notes);
    }
    hd.startElement("","",FriendsXMLConstants.FRIEND_TAG,friendAttrs);
    hd.endElement("","",FriendsXMLConstants.FRIEND_TAG);
  }
}
