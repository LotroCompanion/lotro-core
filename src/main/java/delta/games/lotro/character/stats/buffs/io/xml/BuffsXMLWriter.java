package delta.games.lotro.character.stats.buffs.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.character.stats.buffs.Buff;
import delta.games.lotro.character.stats.buffs.BuffInstance;
import delta.games.lotro.character.stats.buffs.BuffsManager;

/**
 * Writes buffs to XML files.
 * @author DAM
 */
public class BuffsXMLWriter
{
  private static final String CDATA="CDATA";

  /**
   * Write some buffs to the given XML stream.
   * @param hd XML output stream.
   * @param buffs Buffs to write.
   * @throws SAXException If an error occurs.
   */
  public static void write(TransformerHandler hd, BuffsManager buffs) throws SAXException
  {
    if (buffs!=null)
    {
      hd.startElement("","",BuffsXMLConstants.BUFFS_TAG,new AttributesImpl());
      int nb=buffs.getBuffsCount();
      for(int i=0;i<nb;i++)
      {
        AttributesImpl attrs=new AttributesImpl();
        BuffInstance buffInstance=buffs.getBuffAt(i);
        Buff buff=buffInstance.getBuff();
        // ID
        String id=buff.getId();
        attrs.addAttribute("","",BuffsXMLConstants.BUFF_ID_ATTR,CDATA,id);
        // Tier
        Integer tier=buffInstance.getTier();
        if (tier!=null)
        {
          attrs.addAttribute("","",BuffsXMLConstants.BUFF_TIER_ATTR,CDATA,tier.toString());
        }
        // Name
        String label=buff.getLabel();
        if ((label!=null) && (label.length()>0))
        {
          attrs.addAttribute("","",BuffsXMLConstants.BUFF_NAME_ATTR,CDATA,label);
        }
        hd.startElement("","",BuffsXMLConstants.BUFF_TAG,attrs);
        hd.endElement("","",BuffsXMLConstants.BUFF_TAG);
      }
      hd.endElement("","",BuffsXMLConstants.BUFFS_TAG);
    }
  }
}
