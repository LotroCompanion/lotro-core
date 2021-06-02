package delta.games.lotro.kinship.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.kinship.KinshipSummary;

/**
 * Parser for kinship summaries stored in XML.
 * @author DAM
 */
public class KinshipSummaryXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed summary or <code>null</code>.
   */
  public KinshipSummary parseXML(File source)
  {
    KinshipSummary summary=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      summary=new KinshipSummary();
      parseKinship(root, summary);
    }
    return summary;
  }

  /**
   * Read kinship summary attributes from a tag.
   * @param root Tag to read.
   * @param summary Summary to write to.
   */
  public static void parseKinship(Element root, KinshipSummary summary)
  {
    NamedNodeMap attrs=root.getAttributes();
    // ID
    String kinshipIdStr=DOMParsingTools.getStringAttribute(attrs,KinshipXMLConstants.KINSHIP_ID_ATTR,null);
    if (kinshipIdStr!=null)
    {
      InternalGameId kinshipID=InternalGameId.fromString(kinshipIdStr);
      summary.setKinshipID(kinshipID);
    }
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,KinshipXMLConstants.KINSHIP_NAME_ATTR,"");
    summary.setName(name);
    // Leader ID
    String leaderIDStr=DOMParsingTools.getStringAttribute(attrs,KinshipXMLConstants.KINSHIP_LEADER_ID_ATTR,null);
    if (leaderIDStr!=null)
    {
      InternalGameId leaderID=InternalGameId.fromString(leaderIDStr);
      summary.setLeaderID(leaderID);
    }
  }
}
