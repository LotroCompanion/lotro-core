package delta.games.lotro.character.virtues.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.utils.maths.Progression;

/**
 * Writes virtues to XML files.
 * @author DAM
 */
public class VirtueDescriptionXMLWriter
{
  /**
   * Write some virtues to a XML file.
   * @param toFile File to write to.
   * @param virtues Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<VirtueDescription> virtues)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",VirtueDescriptionXMLConstants.VIRTUES_TAG,new AttributesImpl());
        for(VirtueDescription virtue : virtues)
        {
          writeVirtue(hd,virtue);
        }
        hd.endElement("","",VirtueDescriptionXMLConstants.VIRTUES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeVirtue(TransformerHandler hd, VirtueDescription virtue) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=virtue.getIdentifier();
    attrs.addAttribute("","",VirtueDescriptionXMLConstants.VIRTUE_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Key
    String key=virtue.getKey();
    if (key.length()>0)
    {
      attrs.addAttribute("","",VirtueDescriptionXMLConstants.VIRTUE_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Name
    String name=virtue.getName();
    attrs.addAttribute("","",VirtueDescriptionXMLConstants.VIRTUE_NAME_ATTR,XmlWriter.CDATA,name);
    // Icon ID
    int iconId=virtue.getIconId();
    attrs.addAttribute("","",VirtueDescriptionXMLConstants.VIRTUE_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    // Rank stat key
    String rankStatKey=virtue.getRankStatKey();
    if (rankStatKey!=null)
    {
      attrs.addAttribute("","",VirtueDescriptionXMLConstants.VIRTUE_RANK_STAT_KEY_ATTR,XmlWriter.CDATA,rankStatKey);
    }
    // Max rank
    Progression progression=virtue.getMaxRankForCharacterLevelProgression();
    if (progression!=null)
    {
      int progressionId=progression.getIdentifier();
      attrs.addAttribute("","",VirtueDescriptionXMLConstants.VIRTUE_MAX_RANK_ATTR,XmlWriter.CDATA,String.valueOf(progressionId));
    }
    // Description
    String description=virtue.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",VirtueDescriptionXMLConstants.VIRTUE_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }

    hd.startElement("","",VirtueDescriptionXMLConstants.VIRTUE_TAG,attrs);
    // Active stats
    StatsProviderXMLWriter.writeXml(hd,VirtueDescriptionXMLConstants.ACTIVE_STATS_TAG,virtue.getStatsProvider(),null);
    // Passive stats
    StatsProviderXMLWriter.writeXml(hd,VirtueDescriptionXMLConstants.PASSIVE_STATS_TAG,virtue.getPassiveStatsProvider(),null);
    hd.endElement("","",VirtueDescriptionXMLConstants.VIRTUE_TAG);
  }
}
