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
   * @return the loaded summary or <code>null</code>.
   */
  public KinshipSummary parseXML(File source)
  {
    KinshipSummary ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseKinship(root);
    }
    return ret;
  }

  /**
   * Read kinship summary from a tag.
   * @param root Tag to read.
   * @return the loaded summary or <code>null</code>.
   */
  public static KinshipSummary parseKinship(Element root)
  {
    KinshipSummary summary=new KinshipSummary();
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
    return summary;
  }
}
