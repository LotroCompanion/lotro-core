package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.requirements.GloryRankRequirement;

/**
 * XML I/O for glory rank requirements.
 * @author DAM
 */
public class GloryRankRequirementXMLIO implements RequirementSAXWriter<GloryRankRequirement>,RequirementXMLReader<GloryRankRequirement>
{
  @Override
  public GloryRankRequirement readSAX(Attributes attributes)
  {
    Integer requiredGloryRank=SAXParsingTools.getIntegerAttribute(attributes,UsageRequirementXMLConstants.REQUIRED_GLORY_RANK_ATTR,null);
    return buildRequirement(requiredGloryRank);
  }

  @Override
  public GloryRankRequirement readDOM(NamedNodeMap attrs)
  {
    Integer requiredGloryRank=DOMParsingTools.getIntegerAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_GLORY_RANK_ATTR,null);
    return buildRequirement(requiredGloryRank);
  }

  private GloryRankRequirement buildRequirement(Integer rank)
  {
    GloryRankRequirement gloryRankRequirement=null;
    if (rank!=null)
    {
      gloryRankRequirement=new GloryRankRequirement();
      gloryRankRequirement.setRank(rank.intValue());
    }
    return gloryRankRequirement;
  }

  @Override
  public void write(AttributesImpl attrs, GloryRankRequirement gloryRankRequirement)
  {
    if (gloryRankRequirement!=null)
    {
      int rank=gloryRankRequirement.getRank();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_GLORY_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
    }
  }
}
