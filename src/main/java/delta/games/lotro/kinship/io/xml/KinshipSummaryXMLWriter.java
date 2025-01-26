package delta.games.lotro.kinship.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.housing.HouseAddress;
import delta.games.lotro.character.status.housing.io.xml.HousingStatusXMLWriter;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.kinship.KinshipSummary;

/**
 * Writes LOTRO kinship summaries to XML files.
 * @author DAM
 */
public class KinshipSummaryXMLWriter
{
  /**
   * Write a kinship summary to a XML file.
   * @param outFile Output file.
   * @param summary Summary to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final KinshipSummary summary, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl kinshipAttrs=new AttributesImpl();
        write(kinshipAttrs,summary);
        hd.startElement("","",KinshipXMLConstants.KINSHIP_TAG,kinshipAttrs);
        // Address
        HouseAddress address=summary.getAddress();
        if (address!=null)
        {
          HousingStatusXMLWriter.writeAddress(hd,address);
        }
        hd.endElement("","",KinshipXMLConstants.KINSHIP_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write kinship summary attributes.
   * @param kinshipAttrs Attributes to write to.
   * @param kinship Source data.
   */
  public static void write(AttributesImpl kinshipAttrs, KinshipSummary kinship)
  {
    // ID
    InternalGameId kinshipID=kinship.getKinshipID();
    if (kinshipID!=null)
    {
      String kinshipIDStr=kinshipID.asPersistedString();
      kinshipAttrs.addAttribute("","",KinshipXMLConstants.KINSHIP_ID_ATTR,XmlWriter.CDATA,kinshipIDStr);
    }
    // Status date
    long statusDate=kinship.getStatusDate();
    kinshipAttrs.addAttribute("","",KinshipXMLConstants.KINSHIP_STATUS_DATE_ATTR,XmlWriter.CDATA,String.valueOf(statusDate));
    // Name
    String name=kinship.getName();
    if (!name.isEmpty())
    {
      kinshipAttrs.addAttribute("","",KinshipXMLConstants.KINSHIP_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Server
    String server=kinship.getServerName();
    if (!server.isEmpty())
    {
      kinshipAttrs.addAttribute("","",KinshipXMLConstants.KINSHIP_SERVER_ATTR,XmlWriter.CDATA,server);
    }
    // Leader ID
    InternalGameId leaderID=kinship.getLeaderID();
    if (leaderID!=null)
    {
      String leaderIDStr=leaderID.asPersistedString();
      kinshipAttrs.addAttribute("","",KinshipXMLConstants.KINSHIP_LEADER_ID_ATTR,XmlWriter.CDATA,leaderIDStr);
    }
    // Founder ID
    InternalGameId founderID=kinship.getFounderID();
    if (founderID!=null)
    {
      String founderIDstr=founderID.asPersistedString();
      kinshipAttrs.addAttribute("","",KinshipXMLConstants.KINSHIP_FOUNDER_ID_ATTR,XmlWriter.CDATA,founderIDstr);
    }
    // Creation date
    Long creationDate=kinship.getCreationDate();
    if (creationDate!=null)
    {
      kinshipAttrs.addAttribute("","",KinshipXMLConstants.KINSHIP_CREATION_DATE_ATTR,XmlWriter.CDATA,creationDate.toString());
    }
    // MOTD
    String motd=kinship.getMotd();
    if (!motd.isEmpty())
    {
      kinshipAttrs.addAttribute("","",KinshipXMLConstants.KINSHIP_MOTD_ATTR,XmlWriter.CDATA,motd);
    }
  }
}
