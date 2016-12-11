package delta.games.lotro.lore.items.legendary.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.relics.io.xml.RelicXMLConstants;

/**
 * Writes legendary attributes to XML documents.
 * @author DAM
 */
public class LegendaryAttrsXMLWriter
{
  private static final String CDATA="CDATA";

  /**
   * Write legendary attrs to the given XML stream.
   * @param hd XML output stream.
   * @param legendaryAttrs Legendary data to write.
   * @throws Exception
   */
  public static void write(TransformerHandler hd, LegendaryAttrs legendaryAttrs) throws Exception
  {
    // Write relics
    List<Relic> relics=legendaryAttrs.getRelics();
    writeRelic(hd,relics.get(0),RelicType.SETTING);
    writeRelic(hd,relics.get(1),RelicType.GEM);
    writeRelic(hd,relics.get(2),RelicType.RUNE);
    writeRelic(hd,relics.get(3),RelicType.CRAFTED_RELIC);
  }

  private static void writeRelic(TransformerHandler hd, Relic relic, RelicType type) throws Exception
  {
    AttributesImpl relicAttrs=new AttributesImpl();
    relicAttrs.addAttribute("","",LegendaryAttrsXMLConstants.RELIC_TYPE_ATTR,CDATA,type.name());
    if (relic!=null)
    {
      relicAttrs.addAttribute("","",LegendaryAttrsXMLConstants.RELIC_NAME_ATTR,CDATA,relic.getName());
    }
    hd.startElement("","",RelicXMLConstants.RELIC_TAG,relicAttrs);
    hd.endElement("","",RelicXMLConstants.RELIC_TAG);
  }
}
